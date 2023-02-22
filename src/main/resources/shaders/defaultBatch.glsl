#shader vertex
#version 330 core

layout (location = 0) in vec2 a_Position;
layout (location = 1) in vec4 a_Color;

out vec4 f_Color;

uniform mat4 uProjection;

void main()
{
    f_Color = a_Color;
    vec4 test = uProjection * vec4(a_Position.xy, 0.0, 1.0);
    gl_Position = vec4(test.x, test.y, 0, 1);
}

#shader fragment
#version 330 core

in vec4 f_Color;

out vec4 color;

void main()
{
    color = f_Color;
}