/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2009-2019 Caprica Software Limited.
 */

package uk.co.caprica.vlcj.player.embedded;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_instance_t;
import uk.co.caprica.vlcj.player.base.DefaultMediaPlayer;

/**
 * Implementation of a media player that renders video to an embedded Canvas component.
 * <p>
 * This implementation supports the use of an 'overlay' window that will track the video surface
 * position and size. Such an overlay could be used to paint custom graphics over the top of the
 * video.
 * <p>
 * The overlay window should be non-opaque - support for this depends on the JVM, desktop window
 * manager and graphics device hardware and software.
 * <p>
 * The overlay also has some significant limitations, it is a component that covers the video
 * surface component and will prevent mouse and keyboard events from being processed by the video
 * surface. Workarounds to delegate the mouse and keyboard events to the underlying Canvas may be
 * possible but that is a responsibility of the overlay component itself and not these bindings.
 * <p>
 * The overlay will also 'lag' the main application frame when the frame is dragged - the event used
 * to track the frame position does not fire until after the window drag operation has completed
 * (i.e. the mouse pointer is released).
 * <p>
 * A further limitation is that the overlay will not appear when full-screen exclusive mode is used
 * - if an overlay is required in full-screen mode then the full-screen mode must be simulated (by
 * re-sizing the main window, removing decorations and so on).
 * <p>
 * If an overlay is used, then because the window is required to be non-opaque then it will appear
 * in front of <strong>all</strong> other desktop windows, including application dialog windows. For
 * this reason, it may be necessary to disable the overlay while displaying dialog boxes, or when
 * the window is deactivated.
 * <p>
 * The overlay implementation in this class simply keeps a supplied window in sync with the video
 * surface. It is the responsibility of the client application itself to supply an appropriate
 * overlay component.
 * <p>
 * <strong>Finally, the overlay is experimental and support for the overlay may be changed or
 * removed.</strong>
 */
public class DefaultEmbeddedMediaPlayer extends DefaultMediaPlayer implements EmbeddedMediaPlayer {

    /**
     * Native library interface.
     */
    protected final LibVlc libvlc;

    /**
     * Libvlc instance.
     */
    protected final libvlc_instance_t libvlcInstance;

    private final FullScreenService   fullScreenService;
    private final InputService        inputService;
    private final OverlayService      overlayService;
    private final VideoSurfaceService videoSurfaceService;

    /**
     * Create a new media player.
     * <p>
     * Full-screen will not be supported.
     *
     * @param libvlc native interface
     * @param instance libvlc instance
     */
    public DefaultEmbeddedMediaPlayer(LibVlc libvlc, libvlc_instance_t instance) {
        super(libvlc, instance);

        this.libvlc = libvlc;
        this.libvlcInstance = instance;

        this.fullScreenService   = new FullScreenService  (this);
        this.inputService        = new InputService       (this);
        this.overlayService      = new OverlayService     (this);
        this.videoSurfaceService = new VideoSurfaceService(this);
    }

    @Override
    public FullScreenService fullScreen() { // FIXME or fullscreen ?
        return fullScreenService;
    }

    @Override
    public InputService input() {
        return inputService;
    }

    @Override
    public OverlayService overlay() {
        return overlayService;
    }

    @Override
    public VideoSurfaceService videoSurface() {
        return videoSurfaceService;
    }

    @Override
    protected final void onBeforePlay() {
        videoSurface().attachVideoSurface();
    }

    @Override
    protected void onBeforeRelease() {
        fullScreenService  .release();
        inputService       .release();
        overlayService     .release();
        videoSurfaceService.release();
    }

}
