package de.openhpi.squash.view;

import de.openhpi.squash.common.Display;

public abstract class IDrawable {
    public int color;

    public abstract void draw(Display display);
}
