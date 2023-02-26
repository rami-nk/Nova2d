package io.nova.renderer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.lwjgl.opengl.GL30.*;

public class Shader {

    private final String filePath;
    private final int rendererId;
    private final Map<String, Integer> uniformLocationCache;

    public Shader(String filePath) {
        this.filePath = filePath;
        ShaderProgramSource source = parseShader(filePath);
        rendererId = createShader(source.vertexSource(), source.fragmentSource());
        uniformLocationCache = new HashMap<>();
        bind();
    }

    public void bind() {
        glUseProgram(rendererId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void setUniformMat4f(final String name, final Matrix4f mat4f) {
        glUniformMatrix4fv(getUniformLocation(name), false, mat4f.get(BufferUtils.createFloatBuffer(16)));
    }

    public void setUniformVec2f(final String name, final Vector2f vec2f) {
        glUniform2fv(getUniformLocation(name), vec2f.get(BufferUtils.createFloatBuffer(2)));
    }

    public void setUniformInt(final String name, final int value) {
        glUniform1i(getUniformLocation(name), value);
    }

    public void setUniformTexture(final String name, final int slot) {
        setUniformInt(name, slot);
    }

    public void setUniformIntArray(final String name, final int[] values) {
        glUniform1iv(getUniformLocation(name), values);
    }

    public void setUniformTextureArray(final String name, final int[] slots) {
        setUniformIntArray(name, slots);
    }

    private int getUniformLocation(final String name) {
        if (uniformLocationCache.containsKey(name)) {
            return uniformLocationCache.get(name);
        }
        int location = glGetUniformLocation(rendererId, name);
        if (location == -1) {
            System.out.printf("WARNING: uniform \"%s\" does not exist!\n", name);
        }
        uniformLocationCache.put(name, location);
        return location;
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
}