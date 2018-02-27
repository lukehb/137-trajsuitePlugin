package onethreeseven.trajsuitePlugin.graphics;


import java.nio.DoubleBuffer;

/**
 * Tightly packed vertex data such as:
 * vertex, colors, tex coordinates, normals etc.
 * Handles all open-gl offset and stride points too, to reduce likely errors.
 * @author Luke Bermingham
 */
public class PackedVertexData {

    private final Types[] vertexDataTypes;
    private final int totalValuesPerVert;
    private final int nVerts;
    private DoubleBuffer data;

    /**
     * Use this constructor is all data is known ahead of time.
     * @param buf All vertex data. Note, must be true: position() == limit() == capacity().
     * @param vertexDataTypes Which vertex data types: vertex, color, normal etc.?
     */
    public PackedVertexData(DoubleBuffer buf, Types[] vertexDataTypes){

        if(buf.remaining() > 0 || buf.limit() < buf.capacity()){
            throw new IllegalArgumentException("Vertex data buffers passed in this constructor must be fully" +
                    "packed, i.e. the position(), limit(), and capacity() are all equal.");
        }

        int total = 0;
        for (Types valuesPerDataType : vertexDataTypes) {
            total += valuesPerDataType.nValues;
        }
        this.data = buf;
        this.nVerts = buf.capacity() / total;

        this.vertexDataTypes = vertexDataTypes;
        this.totalValuesPerVert = total;
    }

    private static DoubleBuffer positionToEnd(double[] data){
        DoubleBuffer buf = DoubleBuffer.wrap(data);
        buf.position(buf.limit());
        return buf;
    }

    /**
     * Use this constructor is all data is known ahead of time (not incrementally added).
     * @param data All vertex data.
     * @param vertexDataTypes Which vertex data types: vertex, color, normal etc.?
     */
    public PackedVertexData(double[] data, Types[] vertexDataTypes) {
        this(positionToEnd(data), vertexDataTypes);
    }

    /**
     * Use this constructor if you need to add data incrementally.
     * @param verts Total number of vertices when all vertices are eventually inserted.
     * @param vertexDataTypes Which vertex data types: vertex, color, normal etc.?
     */
    protected PackedVertexData(int verts, Types[] vertexDataTypes) {
        this.nVerts = verts;
        int total = 0;
        for (Types valuesPerDataType : vertexDataTypes) {
            total += valuesPerDataType.nValues;
        }
        this.totalValuesPerVert = total;
        this.data = DoubleBuffer.wrap(new double[verts * totalValuesPerVert]);
        this.data.position(0);
        this.vertexDataTypes = vertexDataTypes;
    }

    /**
     * Adds a piece of vertex data to the
     *
     * @param toAdd the data to add
     * @return whether adding was successful
     */
    protected boolean add(double toAdd) {
        if (data.position() < data.limit()) {
            this.data.put(toAdd);
            return true;
        }
        return false;
    }

    public int getNVerts() {
        return nVerts;
    }

    /**
     * Get all the packed data and flush it (assign to null)
     *
     * @return All the data that was flushed
     */
    public double[] flushData() {
        if(this.data.hasArray()){
            double[] out = this.data.array();
            this.data = null;
            return out;
        }
        else{
            double[] flushed = new double[this.data.limit()];
            this.data.position(0);
            this.data.get(flushed);
            this.data = null;
            return flushed;
        }
    }

    public DoubleBuffer flushBuffer(){
        this.data.position(0);
        DoubleBuffer out = this.data;
        this.data = null;
        return out;
    }


    public int getTotalValuesPerVert() {
        return totalValuesPerVert;
    }

    public int getnVerts() {
        return nVerts;
    }

    /**
     * @return A defensive copy of the data types and their packing order.
     */
    public Types[] getVertexDataTypes() {
        Types[] types = new Types[this.vertexDataTypes.length];
        System.arraycopy(this.vertexDataTypes, 0, types, 0, this.vertexDataTypes.length);
        return types;
    }

    /**
     * Kinds of vertex data which are possible
     */
    public enum Types {
        VERTEX(3),
        RGB(3),
        RGBA(4),
        TEXCOORD(2),
        NORMAL(3);

        public final int nValues;

        Types(int nValues) {
            this.nValues = nValues;
        }

    }

}
