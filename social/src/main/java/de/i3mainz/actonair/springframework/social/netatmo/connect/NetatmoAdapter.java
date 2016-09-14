/**
 * 
 */
package de.i3mainz.actonair.springframework.social.netatmo.connect;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import de.i3mainz.actonair.springframework.social.netatmo.api.Netatmo;

/**
 * @author Nikolai Bock
 *
 */
public class NetatmoAdapter implements ApiAdapter<Netatmo> {

    @Override
    public boolean test(Netatmo api) {
        try{
            api.deviceOperations();
            return true;
        }catch(ApiException e){
            return false;
        }
    }

    @Override
    public void setConnectionValues(Netatmo api, ConnectionValues values) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public UserProfile fetchUserProfile(Netatmo api) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateStatus(Netatmo api, String message) {
        // TODO Auto-generated method stub
        
    }

}
