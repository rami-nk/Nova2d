#shader vertex
#version 330 core

layout (location = 0) in vec3 a_Position;
layout (location = 1) in vec2 a_TexCoords;

out vec2 f_TexCoords;

void main()
{
    f_TexCoords = a_TexCoords;
    gl_Position = vec4(a_Position, 1.0);
}

#shader fragment
#version 330 core

in vec2 f_TexCoords;

out vec4 color;

uniform sampler2D u_Texture;

void main()
{
    color = texture(u_Texture, f_TexCoords);
}