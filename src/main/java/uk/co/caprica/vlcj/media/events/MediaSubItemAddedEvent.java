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

package uk.co.caprica.vlcj.media.events;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_event_t;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.binding.internal.media_subitem_added;
import uk.co.caprica.vlcj.media.Media;

final class MediaSubItemAddedEvent extends MediaEvent {

    private final libvlc_media_t subItem;

    MediaSubItemAddedEvent(LibVlc libvlc, Media media, libvlc_event_t event) {
        super(libvlc, media);
        this.subItem = ((media_subitem_added) event.u.getTypedValue(media_subitem_added.class)).new_child;
    }

    @Override
    public void notify(MediaEventListener listener) {
        listener.mediaSubItemAdded(media, subItem);
    }
}
