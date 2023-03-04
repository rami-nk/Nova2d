package io.nova.opengl.renderer;

import io.nova.core.renderer.Shader;
import io.nova.core.renderer.ShaderProgramSource;
import io.nova.core.renderer.ShaderType;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.lwjgl.opengl.GL30.*;

public class OpenGLShader implements Shader {

    private final String filePath;
    private final int rendererId;
    private final Map<String, Integer> uniformLocationCache;

    public OpenGLShader(String filePath) {
        this.filePath = filePath;
        ShaderProgramSource source = parseShader(filePath);
        rendererId = createShader(source.vertexSource(), source.fragmentSource());
        uniformLocationCache = new HashMap<>();
        bind();
    }

    @Override
    public void bind() {
        glUseProgram(rendererId);
    }

    @Override
    public void unbind() {
        glUseProgram(0);
    }

    @Override
    public void setUniformMat4f(final String name, final Matrix4f value) {
        glUniformMatrix4fv(getUniformLocation(name), false, value.get(BufferUtils.createFloatBuffer(16)));
    }

    @Override
    public void setUniformVec2f(final String name, final Vector2f value) {
        glUniform2fv(getUniformLocation(name), value.get(BufferUtils.createFloatBuffer(2)));
    }

    @Override
    public void setUniformInt(final String name, final int value) {
        glUniform1i(getUniformLocation(name), value);
    }

    @Override
    public void setUniformTexture(final String name, final int slot) {
        setUniformInt(name, slot);
    }

    @Override
    public void setUniformIntArray(final String name, final int[] values) {
        glUniform1iv(getUniformLocation(name), values);
    }

    @Override
    public void setUniformTextureArray(final String name, final int[] slots) {
        setUniformIntArray(name, slots);
    }

    @Override
    public void setUniformVec4f(String name, Vector4f value) {
        glUniform4f(getUniformLocation(name), value.x, value.y, value.z, value.w);
    }

    @Override
    public void setUniformFloat(String name, float value) {
        glUniform1f(getUniformLocation(name), value);
    }

    private int createShader(final String vertexShader, final String fragmentShader) {
        int program = glCreateProgram();
        int vs = compileShader(GL_VERTEX_SHADER, vertexShader);
        int fs = compileShader(GL_FRAGMENT_SHADER, fragmentShader);

        glAttachShader(program, vs);
        glAttachShader(program, fs);

        glLinkProgram(program);
        glValidateProgram(program);

        int result = glGetProgrami(program, GL_LINK_STATUS);
        if (result == GL_FALSE) {
            String message = glGetProgramInfoLog(program, glGetProgrami(program, GL_INFO_LOG_LENGTH));
            System.err.printf("Failed to link shader in %s!\n%s",
                    filePath,
                    message);
            return 0;
        }

        glDeleteShader(vs);
        glDeleteShader(fs);

        return program;
    }

    private int compileShader(int type, final String source) {
        int id = glCreateShader(type);
        glShaderSource(id, source);
        glCompileShader(id);

        int result = glGetShaderi(id, GL_COMPILE_STATUS);

        if (result == GL_FALSE) {
            String message = glGetShaderInfoLog(id, glGetShaderi(id, GL_INFO_LOG_LENGTH));
            System.err.printf("Failed to compile %s shader in %s!\n%s",
                    type == ShaderType.VERTEX.ordinal() ? "vertex" : "fragment",
                    filePath,
                    message);
            glDeleteShader(id);
            return 0;
        }

        return id;
    }

    private ShaderProgramSource parseShader(String filePath) {
        try {
            var reader = new BufferedReader(new FileReader(filePath));

            String line = reader.readLine();
            String[] shaderSource = new String[ShaderType.values().length - 1];
            Arrays.fill(shaderSource, "");
            ShaderType type = ShaderType.NONE;
            while (!Objects.isNull(line)) {
                if (line.contains("#shader")) {
                    if (line.contains(ShaderType.VERTEX.name().toLowerCase())) {
                        type = ShaderType.VERTEX;
                    } else if (line.contains(ShaderType.FRAGMENT.name().toLowerCase())) {
                        type = ShaderType.FRAGMENT;
                    }
                } else {
                    shaderSource[type.ordinal()] += line + "\n";
                }
                line = reader.readLine();
            }

            return new ShaderProgramSource(
                    shaderSource[ShaderType.VERTEX.ordinal()],
                    shaderSource[ShaderType.FRAGMENT.ordinal()]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getUniformLocation(String name) {
        if (uniformLocationCache.containsKey(name)) {
            return uniformLocationCache.get(name);
        }
        int location = GL20.glGetUniformLocation(rendererId, name);
        if (location == -1) {
            System.out.printf("WARNING: uniform \"%s\" does not exist!\n", name);
        }
        uniformLocationCache.put(name, location);
        return location;
    }
}