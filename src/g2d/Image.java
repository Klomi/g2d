package g2d;

import g2d.render.Texture;
import org.joml.Vector2d;

import java.util.Objects;

public final class Image {
    private Texture texture = null;

    // Cached Texture Coordinates
    private Vector2d texCoords[] = null;

    private int x = 0, y = 0, w = 0, h = 0;
    private boolean flipH = false, flipV = false;

    public Image() {
    }

    public Image(Texture texture) {
        this.texture = texture;
        this.x = 0;
        this.y = 0;
        this.w = texture.width;
        this.h = texture.height;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        if (this.texture != texture) {
            this.texture = texture;
            texCoords = null;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (this.x != x) {
            this.x = x;
            texCoords = null;
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (this.y != y) {
            this.y = y;
            texCoords = null;
        }
    }

    public int getWidth() {
        return w;
    }

    public void setWidth(int w) {
        if (this.w != w) {
            this.w = w;
            texCoords = null;
        }
    }

    public int getHeight() {
        return h;
    }

    public void setHeight(int h) {
        if (this.h != h) {
            this.h = h;
            texCoords = null;
        }
    }

    public void flipHorizontally(boolean flip) {
        if (this.flipH != flip) {
            this.flipH = flip;
            texCoords = null;
        }
    }

    public void flipVertically(boolean flip) {
        if (this.flipV != flip) {
            this.flipV = flip;
            texCoords = null;
        }
    }

    public Vector2d[] getTexCoords() {
        if (texCoords == null)
            generateTexCoords();
        return texCoords;
    }

    private void generateTexCoords() {
        texCoords = new Vector2d[4];
        texCoords[0] = new Vector2d((double) x / texture.width, 1 - (double) (y + h) / texture.height);
        texCoords[1] = new Vector2d((double) x / texture.width, 1 - (double) y / texture.height);
        texCoords[2] = new Vector2d((double) (x + w) / texture.width, 1 - (double) y / texture.height);
        texCoords[3] = new Vector2d((double) (x + w) / texture.width, 1 - (double) (y + h) / texture.height);

        if (flipH) {
            Vector2d t = texCoords[0];
            texCoords[0] = texCoords[3];
            texCoords[3] = t;

            t = texCoords[1];
            texCoords[1] = texCoords[2];
            texCoords[2] = t;
        }

        if (flipV) {
            Vector2d t = texCoords[0];
            texCoords[0] = texCoords[1];
            texCoords[1] = t;

            t = texCoords[2];
            texCoords[2] = texCoords[3];
            texCoords[3] = t;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return x == image.x &&
                y == image.y &&
                w == image.w &&
                h == image.h &&
                flipH == image.flipH &&
                flipV == image.flipV &&
                texture.id == image.texture.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(texture.id, x, y, w, h, flipH, flipV);
    }
}