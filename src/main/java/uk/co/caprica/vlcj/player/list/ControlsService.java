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

package uk.co.caprica.vlcj.player.list;

public final class ControlsService extends BaseService {

    ControlsService(DefaultMediaListPlayer mediaListPlayer) {
        super(mediaListPlayer);
    }

    /**
     * Play the media list.
     * <p>
     * If the play mode is {@link uk.co.caprica.vlcj.enums.PlaybackMode#REPEAT} no item will be played and a "media list end reached"
     * event will be raised.
     */
    public void play() {
        attachVideoSurface();
        libvlc.libvlc_media_list_player_play(mediaListPlayerInstance);
    }

    /**
     * Toggle-pause the media list.
     */
    public void pause() {
        libvlc.libvlc_media_list_player_pause(mediaListPlayerInstance);
    }

    /**
     * Pause/un-pause the media list.
     *
     * @param pause <code>true</code> to pause; <code>false</code> to un-pause
     */
    public void setPause(boolean pause) {
        libvlc.libvlc_media_list_player_set_pause(mediaListPlayerInstance, pause ? 1 : 0);
    }

    /**
     * Stop the media list.
     */
    public void stop() {
        libvlc.libvlc_media_list_player_stop(mediaListPlayerInstance);
    }

    /**
     * Play a particular item on the media list.
     * <p>
     * When the mode is {@link uk.co.caprica.vlcj.enums.PlaybackMode#REPEAT} this method is the only way to successfully
     * play media in the list.
     *
     * @param itemIndex index of the item to play
     * @return <code>true</code> if the item could be played, otherwise <code>false</code>
     */
    public boolean playItem(int itemIndex) {
        attachVideoSurface();
        return libvlc.libvlc_media_list_player_play_item_at_index(mediaListPlayerInstance, itemIndex) == 0;
    }

    /**
     * Play the next item in the media list.
     * <p>
     * When the mode is {@link uk.co.caprica.vlcj.enums.PlaybackMode#REPEAT} this method will replay the current media,
     * not the next one.
     */
    public boolean playNext() {
        attachVideoSurface();
        return libvlc.libvlc_media_list_player_next(mediaListPlayerInstance) == 0;
    }

    /**
     * Play the previous item in the media list.
     * <p>
     * When the mode is {@link uk.co.caprica.vlcj.enums.PlaybackMode#REPEAT} this method will replay the current media,
     * not the previous one.
     */
    public boolean playPrevious() {
        attachVideoSurface();
        return libvlc.libvlc_media_list_player_previous(mediaListPlayerInstance) == 0;
    }

    private void attachVideoSurface() {
        mediaListPlayer.mediaPlayer().attachVideoSurface();
    }

}
