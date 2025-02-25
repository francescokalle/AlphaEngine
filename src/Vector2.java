public class Vector2 {
    // Members
    public float x;
    public float y;

    // Constructors
    public Vector2() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Compare two vectors
    public boolean equals(Vector2 other) {
        return (this.x == other.x && this.y == other.y);
    }

    public Vector2 add(Vector2 vector2){
        this.x += vector2.x;
        this.y += vector2.y;

        return this;
    }

    public Vector2 subtract(Vector2 vector2){
        this.x -= vector2.x;
        this.y -= vector2.y;

        return this;
    }

    public Vector2 multiply(Vector2 vector2){
        this.x *= vector2.x;
        this.y *= vector2.y;

        return this;
    }

    public Vector2 divide(Vector2 vector2){
        this.x /= vector2.x;
        this.y /= vector2.y;

        return this;
    }

    public Vector2 ZERO(){
        return new Vector2(0, 0);
    }
}
