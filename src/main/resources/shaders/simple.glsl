#shader vertex
#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec4 aColor;

out vec4 fColor;

uniform mat4 uViewProjection;
uniform mat4 uModel;

void main()
{
    fColor = aColor;
    gl_Position = uViewProjection * uModel * vec4(aPosition, 1.0);
}

#shader fragment
#version 330 core

in vec4 fColor;

out vec4 color;

void main()
{
    color = fColor;;
}