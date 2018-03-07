package onethreeseven.trajsuitePlugin.view;

import onethreeseven.trajsuitePlugin.model.VisibleEntity;

/**
 * All the functionality of {@link EntityStateWidget} but also with a
 * toggling icon that represents the visibility state of the entity.
 * @author Luke Bermingham
 */
public class VisibleEntityStateWidget extends EntityStateWidget {

    private final VisibilityWidget visibilityWidget;

    protected VisibleEntityStateWidget(String layername, VisibleEntity entity) {
        super(layername, entity);
        this.visibilityWidget = new VisibilityWidget(entity.isVisibleProperty().get());

        //bi-directional bind of visibility property between the two
        entity.isVisibleProperty().bindBidirectional(this.visibilityWidget.isVisibleProperty());

        //accumulate visibility widget to this node
        getChildren().add(this.visibilityWidget);

    }

    @Override
    public void unbind() {
        super.unbind();
        if(entity instanceof VisibleEntity){
            ((VisibleEntity) entity).isVisibleProperty().unbindBidirectional(this.visibilityWidget.isVisibleProperty());
        }
    }
}
