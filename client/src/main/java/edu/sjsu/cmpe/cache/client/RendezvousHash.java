package edu.sjsu.cmpe.cache.client;

/**
 * Created by emy on 5/6/15.
 */
import com.google.common.hash.HashFunction;

import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListSet;

public class RendezvousHash<N> {

    private final HashFunction hashFunction;

    private int numberOfReplicas;
    private final ConcurrentSkipListSet<N> concurrentSkipListMap;

   public RendezvousHash(HashFunction hashFunction, int numberOfReplicas, Collection<N> concurrentSkipListMap) {
        if(hashFunction==null)
        {
            throw new NullPointerException("hasher");
        }
       if(concurrentSkipListMap==null){
           throw new NullPointerException("Error");
       }
       this.hashFunction=hashFunction;
       this.numberOfReplicas=numberOfReplicas;
       this.concurrentSkipListMap=new ConcurrentSkipListSet<N>(concurrentSkipListMap);
   }

    public boolean remove(N node){
        return concurrentSkipListMap.remove(node);
    }

    public boolean add(N node){
        return concurrentSkipListMap.add(node);
    }

    public N get(Object key) {
        long maxValue = Long.MIN_VALUE;
         N max = null;
        for(N node:concurrentSkipListMap) {
            long serverHash = hashFunction.newHasher().putString(key.toString())
                                .putString(node.toString()).hash().asLong();
            if(serverHash > maxValue) {
                max = node;
                maxValue=serverHash;
            }
        }
        return max;
    }
}

