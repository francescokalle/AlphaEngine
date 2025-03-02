package basics;

public class Vector2 {
    // Members
    public Number x;
    public Number y;

    // Constructors
    public Vector2() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public Vector2(Number x, Number y) {
        this.x = x;
        this.y = y;
    }

    // Compare two vectors (fixed for floating point precision)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector2 other = (Vector2) obj;
        return x.doubleValue() == other.x.doubleValue() &&
                y.doubleValue() == other.y.doubleValue();
    }

    // Returns a new vector sum
    public Vector2 add(Vector2 vector2) {
        return new Vector2(this.x.doubleValue() + vector2.x.doubleValue(),
                this.y.doubleValue() + vector2.y.doubleValue());
    }

    // Returns a new vector difference
    public Vector2 subtract(Vector2 vector2) {
        return new Vector2(this.x.doubleValue() - vector2.x.doubleValue(),
                this.y.doubleValue() - vector2.y.doubleValue());
    }

    // Returns a new vector product
    public Vector2 multiply(Vector2 vector2) {
        return new Vector2(this.x.doubleValue() * vector2.x.doubleValue(),
                this.y.doubleValue() * vector2.y.doubleValue());
    }

    // Returns a new vector division (with zero check)
    public Vector2 divide(Vector2 vector2) {
        return new Vector2(
                vector2.x.doubleValue() != 0 ? this.x.doubleValue() / vector2.x.doubleValue() : 0,
                vector2.y.doubleValue() != 0 ? this.y.doubleValue() / vector2.y.doubleValue() : 0
        );
    }

    // Static zero vector (optimized)
    public static Vector2 ZERO() {
        return new Vector2(0, 0);
    }

    public Vector2 value() {
        return new Vector2(this.x, this.y);
    }

    @Override
    public String toString() {
        return "basics.Vector2{x=" + x + ", y=" + y + "}";
    }
}
