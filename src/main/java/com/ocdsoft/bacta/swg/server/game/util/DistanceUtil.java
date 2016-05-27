package com.ocdsoft.bacta.swg.server.game.util;

/**
 * Created by Kyle on 8/17/2014.
 */
public class DistanceUtil {

    public static int approxDistance( int dx, int dy )
    {
        int min, max, approx;

        if ( dx < 0 ) dx = -dx;
        if ( dy < 0 ) dy = -dy;

        if ( dx < dy )
        {
            min = dx;
            max = dy;
        } else {
            min = dy;
            max = dx;
        }

        approx = ( max * 1007 ) + ( min * 441 );
        if ( max < ( min << 4 ))
            approx -= ( max * 40 );

        // add 512 for proper rounding
        return (( approx + 512 ) >> 10 );
    }

    public static double distance( int dx, int dy ) {
        return  Math.sqrt((dx * dx) + (dy * dy));
    }

    public static double distanceSquared( double dx, double dy ) {
        return  (dx * dx) + (dy * dy);
    }

}
