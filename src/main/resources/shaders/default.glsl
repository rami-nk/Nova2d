#type vertex
#version 330 core

layout (location = 0) in vec3 a_Position;
layout (location = 1) in vec4 a_Color;

out vec4 f_Color;

void main()
{
    f_Color = a_Color;
    gl_Position = vec4(a_Position, 1.0);
}

#type fragment
#version 330 core

in vec4 f_Color;

out vec4 color;

void main()
{
    color = f_Color;
}