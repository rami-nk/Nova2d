#shader vertex
#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec4 color;

out vec4 f_Color;

void main()
{
    f_Color = color;
    gl_Position = vec4(position, 1.0);
}

#shader fragment
#version 330 core

in vec4 f_Color;
layout(location = 0) out vec4 color;

void main()
{
    color = f_Color;
}