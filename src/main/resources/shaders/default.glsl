#shader vertex
#version 330 core

layout (location = 0) in vec3 a_Position;
layout (location = 1) in vec4 a_Color;

out vec4 f_Color;

uniform mat4 uProjection;
uniform mat4 uView;

void main()
{
    f_Color = a_Color;
    gl_Position = uProjection * uView * vec4(a_Position, 1.0);
}

#shader fragment
#version 330 core

in vec4 f_Color;

out vec4 color;

void main()
{
    color = f_Color;
}