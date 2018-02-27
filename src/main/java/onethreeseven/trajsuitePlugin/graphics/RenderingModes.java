package onethreeseven.trajsuitePlugin.graphics;

/**
 * Which rendering mode to use for a {@link GraphicsPayload}.
 * @author Luke Bermingham
 */
public enum RenderingModes {
    POINTS(0),
    LINES(1),
    LINE_LOOP(2),
    LINE_STRIP(3),
    TRIANGLES(4),
    TRIANGLE_STRIP(5),
    TRIANGLE_FAN(6),
    QUADS(7),
    QUAD_STRIP(8),
    POLYGON(9);

    public final int mode;
    RenderingModes(int mode){
        this.mode = mode;
    }
}

//
//    int lines = GL2.GL_LINES; // 1
//    int pts = GL2.GL_POINTS; //0
//    int lineStrip = GL2.GL_LINE_STRIP; //3
//    int lineloop = GL2.GL_LINE_LOOP; //2
//    int tris = GL2.GL_TRIANGLES; //4
//    int triStrip = GL2.GL_TRIANGLE_STRIP; //5
//    int triFan = GL2.GL_TRIANGLE_FAN; //6
//    int quads = GL2.GL_QUADS; //7
//    int qaudStrip = GL2.GL_QUAD_STRIP; //8
//    int poly = GL2.GL_POLYGON; //9