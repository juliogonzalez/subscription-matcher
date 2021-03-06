/**
 * Copyright (c) 2017 SUSE LLC
 *
 * This software is licensed to you under the GNU General Public License,
 * version 2 (GPLv2). There is NO WARRANTY for this software, express or
 * implied, including the implied warranties of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. You should have received a copy of GPLv2
 * along with this software; if not, see
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt.
 *
 * Red Hat trademarks are not licensed under GPLv2. No permission is
 * granted to use or replicate Red Hat trademarks that are incorporated
 * in this software or its documentation.
 */

package com.suse.matcher.facts;

import org.kie.api.definition.type.PropertyReactive;

/**
 * Penalty used for 1-2 subscriptions only.
 * {@see Penalty}
 */
@PropertyReactive
public class OneTwoPenalty extends Penalty {
    /**
     * Instantiates a new penalty.
     *
     * @param subscriptionIdIn the subscription id
     * @param penaltyGroupIdIn the penalty group id
     * @param centsIn          the penalty cents
     */
    public OneTwoPenalty(Long subscriptionIdIn, int penaltyGroupIdIn, int centsIn) {
        super(subscriptionIdIn, penaltyGroupIdIn, centsIn);
    }
}
