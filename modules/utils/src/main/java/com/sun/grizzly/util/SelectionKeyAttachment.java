/*
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2008 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 *
 */

package com.sun.grizzly.util;

import java.nio.channels.SelectionKey;

/**
 * Basic class for all SelectionKey attachments.
 * Custom attachments should be inherited from it.
 *
 * @author Alexey Stashok
 */
public abstract class SelectionKeyAttachment {
    public static final long UNLIMITED_TIMEOUT = Long.MIN_VALUE;
    
    protected long timeout = UNLIMITED_TIMEOUT;

    public static Object getAttachment(SelectionKey key) {
        Object attachment = key.attachment();
        if (attachment instanceof SelectionKeyAttachmentWrapper) {
            return ((SelectionKeyAttachmentWrapper) attachment).getAttachment();
        }

        return attachment;
    }

    /**
     * returns the idle timeout delay.
     * default it returns Long.MIN_VALUE , meaning null.
     * -1 means no timeout.
     * Subclass need to override it.
     * @return
     */
    public long getIdleTimeoutDelay(){
        return UNLIMITED_TIMEOUT;
    }

    /**
     *  Subclass need to override this method for it to work.
     *  Long.MIN_VALUE  means null , and default value will be used.
     * -1 means no timeout.
     * @param idletimeoutdelay
     */
    public void setIdleTimeoutDelay(long idletimeoutdelay){
        throw new IllegalStateException("setIdleTimeoutDelay not implemented in subclass");
    }

    
    public long getTimeout() {
        return timeout;
    }
    
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     *  called when idle timeout detected.
     *  return true if key should be canceled.
     * @param Key
     * @return
     */
    public boolean timedOut(SelectionKey Key){
        return true;
    }


    public void release(SelectionKey selectionKey) {
        timeout = UNLIMITED_TIMEOUT;
    }
}
