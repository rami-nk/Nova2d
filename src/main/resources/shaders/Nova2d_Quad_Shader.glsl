#shader vertex
#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec4 aColor;
layout (location = 2) in vec2 aTexCoords;
layout (location = 3) in float aTexId;
layout (location = 4) in float aTilingFactor;
layout (location = 5) in float aEntityID;

uniform mat4 uViewProjection;

out vec2 fTexCoords;
out vec4 fColor;
out float fTexId;
out float fTilingFactor;
out float fEntityID;

void main()
{
    fTexCoords = aTexCoords;
    fColor = aColor;
    fTexId = aTexId;
    fTilingFactor = aTilingFactor;
    fEntityID = aEntityID;
    gl_Position = uViewProjection * vec4(aPosition, 1.0);
}

#shader fragment
#version 330 core

in vec2 fTexCoords;
in vec4 fColor;
in float fTexId;
in float fTilingFactor;
in float fEntityID;

layout (location = 0) out vec4 color;
layout (location = 1) out int id;

uniform sampler2D uTextures[16];

void main()
{
    color = texture(uTextures[int(fTexId)], fTexCoords * fTilingFactor) * fColor;
    id = int(fEntityID);
}