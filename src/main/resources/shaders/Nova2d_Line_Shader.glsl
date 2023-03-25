#shader vertex
#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec4 aColor;

uniform mat4 uViewProjection;

out vec4 fColor;

void main()
{
    fColor = aColor;

    gl_Position = uViewProjection * vec4(aPosition, 1.0);
}

#shader fragment
#version 330 core

in vec4 fColor;

layout (location = 0) out vec4 color;

void main()
{
    color = fColor;
}