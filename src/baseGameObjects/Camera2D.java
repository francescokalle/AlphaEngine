package baseGameObjects;

import basics.Input;
import basics.Vector2;
import graphics.GamePanel;

public class Camera2D extends GameObject {
    private boolean isActive = true;
    private boolean zoomEnabled = true;
    private boolean smoothZoomEnabled = true;
    private Vector2 offset = Vector2.ZERO();
    private float zoom = 1.0f;
    private float targetZoom = 1.0f;
    private float zoomSpeed = 0.25f;
    private float zoomSmoothing = 0.1f;
    private float zoomAccumulator = 0f;

    public Camera2D(GamePanel gamePanel, Vector2 position) {
        super(gamePanel, position, new Vector2(0, 0));
    }

    @Override
    public void update() {
        super.update();

        if (isActive && zoomEnabled) {
            // Gestione dello zoom
            int wheelRotation = Input.getMouseWheelRotation();
            if (wheelRotation != 0) {
                zoomAccumulator -= wheelRotation * zoomSpeed;

                // Calcola il target zoom
                float zoomFactor = 1 + zoomAccumulator;
                targetZoom = Math.max(0.1f, Math.min(5.0f, zoom * zoomFactor));
                zoomAccumulator = 0f;
            }

            // Applica lo zoom (smooth o immediato)
            if (smoothZoomEnabled) {
                // Zoom fluido
                zoom += (targetZoom - zoom) * zoomSmoothing;
                if (Math.abs(targetZoom - zoom) < 0.001f) {
                    zoom = targetZoom;
                }
            } else {
                // Zoom immediato
                zoom = targetZoom;
            }

            // Calcola la posizione centrata
            Vector2 centeredPosition = new Vector2(
                    getAbsolutePosition().x.doubleValue() - (gamePanel.getWidth()/2.0),
                    getAbsolutePosition().y.doubleValue() - (gamePanel.getHeight()/2.0)
            ).add(offset);

            gamePanel.setCameraPosition(centeredPosition);
            gamePanel.setCameraZoom(zoom);
        }
    }

    // Metodi per gestire lo zoom
    public void setZoomEnabled(boolean enabled) {
        this.zoomEnabled = enabled;
        if (!enabled) {
            // Resetta lo zoom quando viene disabilitato
            targetZoom = 1.0f;
            zoom = 1.0f;
        }
    }

    public void setSmoothZoomEnabled(boolean enabled) {
        this.smoothZoomEnabled = enabled;
    }

    public void setZoomSpeed(float speed) {
        this.zoomSpeed = Math.max(0.01f, Math.min(0.2f, speed));
    }

    public void setZoomSmoothing(float smoothing) {
        this.zoomSmoothing = Math.max(0.01f, Math.min(0.5f, smoothing));
    }

    // Altri metodi esistenti...
    public void setOffset(Vector2 offset) {
        this.offset = offset;
    }

    public float getZoom() {
        return zoom;
    }

    public boolean isZoomEnabled() {
        return zoomEnabled;
    }

    public boolean isSmoothZoomEnabled() {
        return smoothZoomEnabled;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }
}