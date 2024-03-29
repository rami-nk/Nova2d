#shader vertex
#version 330 core

layout (location = 0) in vec3 aPosition;

uniform mat4 uViewProjection;
uniform mat4 uModel;

void main()
{
    gl_Position = uViewProjection * uModel * vec4(aPosition, 1.0);
}

#shader fragment
#version 330 core

out vec4 color;

uniform vec4 uColor;

void main()
{
    color = uColor;
}