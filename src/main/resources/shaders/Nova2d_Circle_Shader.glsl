#shader vertex
#version 330 core

layout (location = 0) in vec3 aWorldPosition;
layout (location = 1) in vec3 aLocalPosition;
layout (location = 2) in vec4 aColor;
layout (location = 3) in float aThickness;
layout (location = 4) in float aFade;
layout (location = 5) in float aEntityID;

uniform mat4 uViewProjection;

out vec3 fLocalPosition;
out vec4 fColor;
out float fThickness;
out float fFade;
out float fEntityID;

void main()
{
    fLocalPosition = aLocalPosition;
    fColor = aColor;
    fThickness = aThickness;
    fFade = aFade;
    fEntityID = aEntityID;

    gl_Position = uViewProjection * vec4(aWorldPosition, 1.0);
}

#shader fragment
#version 330 core

in vec3 fLocalPosition;
in vec4 fColor;
in float fThickness;
in float fFade;
in float fEntityID;

layout (location = 0) out vec4 color;
layout (location = 1) out int id;

void main()
{
    float distance = 1.0 - length(fLocalPosition);
    float alpha = smoothstep(0.0, fFade, distance);
    alpha *= smoothstep(fThickness + fFade, fThickness, distance);

    if (alpha < 0.01)
    discard;

    color = fColor;
    color.a *= alpha;
    id = int(fEntityID);
}