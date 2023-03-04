#shader vertex
#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec2 aTexCoords;

uniform mat4 uViewProjection;
uniform mat4 uModel;

out vec2 fTexCoords;

void main()
{
    fTexCoords = aTexCoords;
    gl_Position = uViewProjection * uModel * vec4(aPosition, 1.0);
}

#shader fragment
#version 330 core

in vec2 fTexCoords;

out vec4 color;

uniform sampler2D uTexture;

void main()
{
    color = texture(uTexture, fTexCoords);
}