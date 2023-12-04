package org.rmj.guanzongroup.gsecurity.mockdata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.guanzongroup.gsecurity.pojo.itinerary.PatrolRoute;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Everything inside this class is mock data for demonstration purposes only...
 * Delete mock data package after integrating Back-End APIs and retrieving actual
 * data needed for full operation...
 */
public class ListPatrolRoute {

    public static LiveData<List<PatrolRoute>> getPatrolRoute(){
        MutableLiveData<List<PatrolRoute>> itineraryList = new MutableLiveData<>();

        List<PatrolRoute> list = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i <= 15; i++) {
            PatrolRoute patrolRoute = new PatrolRoute();
            patrolRoute.setDescription(getRandomPlace());
            patrolRoute.setCategory(getRandomCategory());
            patrolRoute.setWarehouse("Warehouse " + getRandomWarehouse());
            patrolRoute.setVisited(random.nextBoolean());
            patrolRoute.setPatrolType("Patrol");

            list.add(patrolRoute);
        }

        itineraryList.postValue(list);
        return itineraryList;
    }

    private static String getRandomPlace() {
        String[] places = {"Warehouse Back Door", "Stock Room Entrance", "Warehouse Hallway", "Warehouse Mechanic Bay", "Warehouse Front Desk"};
        Random random = new Random();
        return places[random.nextInt(places.length)];
    }

    private static String getRandomCategory() {
        String[] categories = {"Security Patrol", "Facilitator Check"};
        Random random = new Random();
        return categories[random.nextInt(categories.length)];
    }

    private static String getRandomWarehouse() {
        String[] warehouses = {"1", "2", "3", "4", "5"};
        Random random = new Random();
        return warehouses[random.nextInt(warehouses.length)];
    }
}
