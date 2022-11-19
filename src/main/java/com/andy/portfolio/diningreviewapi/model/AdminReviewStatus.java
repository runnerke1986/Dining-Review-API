package com.andy.portfolio.diningreviewapi.model;

/* No getter or setter defined because only the status will be used to determine which
 reviews need approval or rejection from the admin. Can be expanded later for example to create log files */

public enum AdminReviewStatus {
    PENDING,
    ACCEPTED,
    REJECTED;
}
