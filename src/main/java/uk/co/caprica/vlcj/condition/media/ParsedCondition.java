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

package uk.co.caprica.vlcj.condition.media;

import uk.co.caprica.vlcj.enums.MediaParsedStatus;
import uk.co.caprica.vlcj.media.Media;

/**
 * Implementation of a condition that waits for the media player to report that media has been parsed successfully.
 */
public class ParsedCondition extends MediaCondition<Object> {

    /**
     * Create a condition.
     *
     * @param media media
     */
    public ParsedCondition(Media media) {
        super(media);
    }

    @Override
    public final void mediaParsedChanged(Media media, MediaParsedStatus newStatus) {
        if (newStatus == MediaParsedStatus.DONE) {
            ready();
        } else {
            error();
        }
    }

}
