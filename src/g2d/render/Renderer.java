package g2d.render;

import g2d.render.shaders.DefaultShaderProgram;
import g2d.render.shaders.ShaderProgram;
import org.joml.Matrix4d;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Renderer {
    public static final int MAX_VERTEX_COUNT = 40000;
    public static Texture blankTexture = null;
    public static ShaderProgram program = null;

    public static int vao = 0;
    public static int pos_vbo = 0;
    public static int color_vbo = 0;
    public static int texCoord_vbo = 0;

    public static FloatBuffer posData = null;
    public static FloatBuffer colorData = null;
    public static FloatBuffer texCoordData = null;

    public static List<DrawBatch> batches = new LinkedList<>();
    public static DrawBatch currentBatch = null;
    public static int indexOffset = 0;

    public static void init() {
        blankTexture = new BlankTexture();
        program = new DefaultShaderProgram();
        vao = glGenVertexArrays();
        pos_vbo = glGenBuffers();
        color_vbo = glGenBuffers();
        texCoord_vbo = glGenBuffers();
        posData = BufferUtils.createFloatBuffer(MAX_VERTEX_COUNT * 2 * 4);
        colorData = BufferUtils.createFloatBuffer(MAX_VERTEX_COUNT * 4 * 4);
        texCoordData = BufferUtils.createFloatBuffer(MAX_VERTEX_COUNT * 2 * 4);

        program.bind();
        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, pos_vbo);
        glBufferData(GL_ARRAY_BUFFER, MAX_VERTEX_COUNT * 2 * 4, GL_DYNAMIC_DRAW);
        glVertexAttribPointer(program.varLocations.get("in_pos"), 2, GL_FLOAT, false, 2 * 4, 0);
        glEnableVertexAttribArray(program.varLocations.get("in_pos"));

        glBindBuffer(GL_ARRAY_BUFFER, color_vbo);
        glBufferData(GL_ARRAY_BUFFER, MAX_VERTEX_COUNT * 4 * 4, GL_DYNAMIC_DRAW);
        glVertexAttribPointer(program.varLocations.get("in_color"), 4, GL_FLOAT, false, 4 * 4, 0);
        glEnableVertexAttribArray(program.varLocations.get("in_color"));

        glBindBuffer(GL_ARRAY_BUFFER, texCoord_vbo);
        glBufferData(GL_ARRAY_BUFFER, MAX_VERTEX_COUNT * 2 * 4, GL_DYNAMIC_DRAW);
        glVertexAttribPointer(program.varLocations.get("in_texCoord"), 2, GL_FLOAT, false, 2 * 4, 0);
        glEnableVertexAttribArray(program.varLocations.get("in_texCoord"));

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public static void push(Primitive prim) {
        if (indexOffset + prim.vertexCount >= MAX_VERTEX_COUNT)
            flush();

        if ((currentBatch == null) || (!currentBatch.rs.canBatchWith(prim.rs))) {
            if (currentBatch != null)
                batches.add(currentBatch);
            currentBatch = new DrawBatch();
            currentBatch.rs = prim.rs;
            currentBatch.startIndex = indexOffset;
        }

        prim.flipBuffers();
        posData.put(prim.posData);
        colorData.put(prim.colorData);
        texCoordData.put(prim.texCoordData);

        currentBatch.vertexCount += prim.vertexCount;
        indexOffset += prim.vertexCount;
    }

    public static void flush() {
        program.bind();
        if (currentBatch != null)
            batches.add(currentBatch);

        posData.flip();
        colorData.flip();
        texCoordData.flip();
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, pos_vbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, posData);
        glBindBuffer(GL_ARRAY_BUFFER, color_vbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, colorData);
        glBindBuffer(GL_ARRAY_BUFFER, texCoord_vbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, texCoordData);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glActiveTexture(GL_TEXTURE0);
        for (DrawBatch batch : batches) {
            batch.rs.blendMode.apply();
            glBindTexture(GL_TEXTURE_2D, batch.rs.tID);
            glDrawArrays(batch.rs.primType, batch.startIndex, batch.vertexCount);
            glBindTexture(GL_TEXTURE_2D, 0);
        }
        glFlush();
        glBindVertexArray(0);

        batches.clear();
        posData.clear();
        colorData.clear();
        texCoordData.clear();
        indexOffset = 0;
        currentBatch = null;
    }

    public static void setMatrix(Matrix4d matrix) {
        float f[] = new float[16];
        matrix.get(f);
        glUniformMatrix4fv(program.varLocations.get("matrix"), false, f);
    }
}