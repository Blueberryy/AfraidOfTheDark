package com.DavidM1A2.afraidofthedark.client.gui.base;

import com.DavidM1A2.afraidofthedark.client.gui.eventListeners.*;
import com.DavidM1A2.afraidofthedark.client.gui.events.AOTDKeyEvent;
import com.DavidM1A2.afraidofthedark.client.gui.events.AOTDMouseEvent;
import org.lwjgl.util.Point;

/**
 * A base class for any GUI components that can listen for action events like mouse clicks
 */
public abstract class AOTDGuiComponentWithEvents extends AOTDGuiComponent
{
    // The key listener of this component
    private IAOTDKeyListener keyListener;
    // The mouse listener of this component
    private IAOTDMouseListener mouseListener;
    // The mouse move listener of this component
    private IAOTDMouseMoveListener mouseMoveListener;

    /**
     * Constructor initializes the bounding box
     *
     * @param x      The X location of the top left corner
     * @param y      The Y location of the top left corner
     * @param width  The width of the component
     * @param height The height of the component
     */
    public AOTDGuiComponentWithEvents(Integer x, Integer y, Integer width, Integer height)
    {
        super(x, y, width, height);
        // Add a mouse move listener to this control that allows us to fire off events whenever conditions are met
        this.addMouseMoveListener(new AOTDMouseMoveListener()
        {
            @Override
            public void mouseMoved(AOTDMouseEvent event)
            {
                // Grab the source of the event
                AOTDGuiComponentWithEvents component = event.getSource();
                // Store the flag telling us if the component was hovered
                boolean wasHovered = component.isHovered();
                // Set the hovered flag based on if we intersect the component
                component.setHovered(component.intersects(new Point(event.getMouseX(), event.getMouseY())));
                // Fire mouse enter/exit events if our mouse entered or exited the control
                if (component.isHovered() && !wasHovered)
                {
                    component.processMouseInput(new AOTDMouseEvent(component, event.getMouseX(), event.getMouseY(), AOTDMouseEvent.MouseButtonClicked.Other, AOTDMouseEvent.MouseEventType.Enter));
                }
                if (!component.isHovered() && wasHovered)
                {
                    component.processMouseInput(new AOTDMouseEvent(component, event.getMouseX(), event.getMouseY(), AOTDMouseEvent.MouseButtonClicked.Other, AOTDMouseEvent.MouseEventType.Exit));
                }
            }
        });
    }

    /**
     * Called to process a mouse input event
     *
     * @param event The event to process
     */
    public void processMouseInput(AOTDMouseEvent event)
    {
        // If the event is consumed, don't do anything
        if (event.isConsumed())
        {
            return;
        }
        // If the event is an enter or exit event, consume the event. This is because an enter and exit event can only happen to a single control at a time
        if (event.getEventType() == AOTDMouseEvent.MouseEventType.Enter || event.getEventType() == AOTDMouseEvent.MouseEventType.Exit)
        {
            event.consume();
        }
        // We set the source to be this component, because we are processing it
        event.setSource(this);
        // If we have a mouse move listener, and the event is a move or drag, fire off our mouse move listener
        if (mouseMoveListener != null && (event.getEventType() == AOTDMouseEvent.MouseEventType.Move || event.getEventType() == AOTDMouseEvent.MouseEventType.Drag))
        // Get the event type and process accordingly
        {
            switch (event.getEventType())
            {
                case Move:
                    mouseMoveListener.mouseMoved(event);
                    break;
                case Drag:
                    mouseMoveListener.mouseDragged(event);
                    break;
                default:
                    return;
            }
        }
        // If we have a mouse listener, process a mouse event
        if (mouseListener != null)
        // Get the event type and process accordingly
        {
            switch (event.getEventType())
            {
                case Click:
                    mouseListener.mouseClicked(event);
                    break;
                case Enter:
                    mouseListener.mouseEntered(event);
                    break;
                case Exit:
                    mouseListener.mouseExited(event);
                    break;
                case Press:
                    mouseListener.mousePressed(event);
                    break;
                case Release:
                    mouseListener.mouseReleased(event);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Called to process a key input event
     *
     * @param event The event to process
     */
    public void processKeyInput(AOTDKeyEvent event)
    {
        // We set the source to be this component, because we are processing it
        event.setSource(this);
        // If we have a key listener, process a key event
        if (keyListener != null)
        {
            switch (event.getEventType())
            {
                case Type:
                    keyListener.keyTyped(event);
                    break;
                case Press:
                    keyListener.keyPressed(event);
                    break;
                case Release:
                    keyListener.keyReleased(event);
                    break;
            }
        }
    }

    /**
     * Adds a key listener to this control to be fired whenever a key event is raised
     *
     * @param keyListener The key listener to add
     */
    public void addKeyListener(IAOTDKeyListener keyListener)
    {
        if (keyListener == null)
        {
            return;
        }
        // Combine the current key listener with the new one to make the next key listener
        this.keyListener = AOTDEventMulticaster.combineKeyListeners(this.keyListener, keyListener);
    }

    /**
     * Adds a mouse listener to this control to be fired whenever a mouse event is raised
     *
     * @param mouseListener The mouse listener to add
     */
    public void addMouseListener(IAOTDMouseListener mouseListener)
    {
        if (mouseListener == null)
        {
            return;
        }
        // Combine the current mouse listener with the new one to make the next mouse listener
        this.mouseListener = AOTDEventMulticaster.combineMouseListeners(this.mouseListener, mouseListener);
    }

    /**
     * Adds a mouse move listener to this control to be fired whenever a mouse move event is raised
     *
     * @param mouseMoveListener The mouse move listener to add
     */
    public void addMouseMoveListener(IAOTDMouseMoveListener mouseMoveListener)
    {
        if (mouseMoveListener == null)
        {
            return;
        }
        // Combine the current mouse move listener with the new one to make the next mouse move listener
        this.mouseMoveListener = AOTDEventMulticaster.combineMouseMoveListeners(this.mouseMoveListener, mouseMoveListener);
    }
}
