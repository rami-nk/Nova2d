#shader vertex
#version 330 core

layout (location = 0) in vec2 a_Position;
layout (location = 1) in vec4 a_Color;
layout (location = 2) in vec2 a_TextureCoordinates;
layout (location = 3) in float a_TextureId;

out vec4 f_Color;
out vec2 f_TextureCoordinates;
out float f_TextureId;

uniform mat4 u_Projection;

void main()
{
    f_Color = a_Color;
    f_TextureCoordinates = a_TextureCoordinates;
    f_TextureId = a_TextureId;

    vec4 test = u_Projection * vec4(a_Position.xy, 0.0, 1.0);
    gl_Position = vec4(test.x, test.y, 0, 1);
}

#shader fragment
#version 330 core

in vec4 f_Color;
in vec2 f_TextureCoordinates;
in float f_TextureId;

uniform sampler2D u_Textures[16];

out vec4 color;

void main()
{
    int id = int(f_TextureId);
    if (id == -1) {
        color = f_Color;
    } else {
        color = texture(u_Textures[int(f_TextureId)], f_TextureCoordinates);
    }
}