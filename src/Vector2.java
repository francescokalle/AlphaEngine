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

    // Compare two vectors (fixed for floating point precision)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector2 other = (Vector2) obj;
        return Float.compare(this.x, other.x) == 0 &&
                Float.compare(this.y, other.y) == 0;
    }

    // Returns a new vector sum
    public Vector2 add(Vector2 vector2) {
        return new Vector2(this.x + vector2.x, this.y + vector2.y);
    }

    // Returns a new vector difference
    public Vector2 subtract(Vector2 vector2) {
        return new Vector2(this.x - vector2.x, this.y - vector2.y);
    }

    // Returns a new vector product
    public Vector2 multiply(Vector2 vector2) {
        return new Vector2(this.x * vector2.x, this.y * vector2.y);
    }

    // Returns a new vector division (with zero check)
    public Vector2 divide(Vector2 vector2) {
        return new Vector2(
                vector2.x != 0 ? this.x / vector2.x : 0,
                vector2.y != 0 ? this.y / vector2.y : 0
        );
    }

    // Static zero vector (optimized)
    public static Vector2 ZERO() {
        return new Vector2(0, 0);
    }

    @Override
    public String toString() {
        return "Vector2{x=" + x + ", y=" + y + "}";
    }
}
