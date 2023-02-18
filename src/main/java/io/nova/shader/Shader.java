package io.nova.shader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private final int rendererId;

    public Shader(String filePath) {
        ShaderProgramSource source = parseShader(filePath);
        rendererId = createShader(source.vertexSource(), source.fragmentSource());
    }

    public void bind() {
        glUseProgram(rendererId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    private int createShader(final String vertexShader, final String fragmentShader) {
        int program = glCreateProgram();
        int vs = compileShader(GL_VERTEX_SHADER, vertexShader);
        int fs = compileShader(GL_FRAGMENT_SHADER, fragmentShader);

        glAttachShader(program, vs);
        glAttachShader(program, fs);

        glLinkProgram(program);
        glValidateProgram(program);

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
            System.err.printf("Failed to compile %s shader!\n%s",
                    type == ShaderType.VERTEX.ordinal() ? "vertex" : "fragment",
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