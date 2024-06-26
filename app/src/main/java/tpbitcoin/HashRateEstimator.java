package tpbitcoin;

import org.bitcoinj.core.Sha256Hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashRateEstimator {
    private final int duration; // Duration of each experiment, in milliseconds
    private final int numberOfTries; // Number of experiments to run

    /**
     * Create a new object for estimating the number of SHA256 hashes the host can perform per second.
     *
     * @param duration      Duration of each experiment, in milliseconds
     * @param numberOfTries Number of experiments to run
     */
    public HashRateEstimator(int duration, int numberOfTries) {
        this.duration = duration;
        this.numberOfTries = numberOfTries;
    }

    /**
     * @return Return the hash rate (hashes per second)
     */
    public double estimate() {
        long totalHashes = 0;
        long startTime, endTime, elapsedTime;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return 0.0; // Return 0 if SHA-256 algorithm is not available
        }

        for (int i = 0; i < numberOfTries; i++) {
            startTime = System.currentTimeMillis();
            totalHashes += performSHA256Hash(md);
            endTime = System.currentTimeMillis();
            elapsedTime = endTime - startTime;

            // Sleep for the remaining duration if needed
            if (elapsedTime < duration) {
                try {
                    Thread.sleep(duration - elapsedTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        double averageHashesPerSecond = (double) totalHashes / (duration * numberOfTries / 1000.0);
        return averageHashesPerSecond;
    }

    /**
     * Perform a single SHA-256 hash.
     *
     * @param md MessageDigest instance for SHA-256
     * @return Number of hashes performed (1)
     */
    private int performSHA256Hash(MessageDigest md) {
        // Dummy data to hash (can be anything)
        byte[] data = new byte[64];
        new java.util.Random().nextBytes(data);

        // Perform SHA-256 hash
        byte[] hash = md.digest(data);

        // Return the number of hashes performed (always 1 in this case)
        return 1;
    }


}
