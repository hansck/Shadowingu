/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.hansck.shadowingu.util;

public class DynamicTimeWrapping {

	protected int globalPathConstraint = 20;
	private double[][] test;
	private double[][] reference;
	// variance for each feature dimension
	private double[] variance;

	public DynamicTimeWrapping(double[][] test, double[][] reference) {
		this.test = test;
		this.reference = reference;
		// by default, we initialize variance as 1 for each dimension.
		variance = new double[test[0].length];
		for (int i = 0; i < variance.length; i++)
			variance[i] = 1;
	}

	public DynamicTimeWrapping(double[][] test, double[][] reference, double[] variance) {
		this.test = test;
		this.reference = reference;
		this.variance = variance;
	}

	public void setGlobalPathConstraint(int globalPathConstraint) {
		this.globalPathConstraint = globalPathConstraint;
	}

	/**
	 * Calculate the distance between two feature vectors.
	 * The feature vector can be 1-dimensional feature vector OR
	 * 2-dimensioanl feature vector.  For speech processing, we usually
	 * represent audio signal as MFCCs features, where each entry is again
	 * 1-dimensional feature vector (we usually choose the first 12 cofficients
	 * for MFCCs)
	 *
	 * @return
	 */
	public double calDistance() {

		int n = test.length;
		int m = reference.length;

		// DP for calculating the minimum distance between two vector.
		// DTW[i,j] = minimum distance between vector test[0..i] and reference[0..j]
		double[][] DTW = new double[n][m];

		// initialization
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				DTW[i][j] = Double.MAX_VALUE;

		// initialize base case
		DTW[0][0] = getDistance(test[0], reference[0]);

		// initialize boundary condition.
		for (int i = 1; i < n; i++)
			DTW[i][0] = DTW[i - 1][0] + getDistance(test[i], reference[0]);

		for (int i = 1; i < m; i++)
			DTW[0][i] = DTW[0][i - 1] + getDistance(test[0], reference[i]);

		// DP comes here...
		for (int i = 1; i < n; i++) {
			for (int j = Math.max(1, i - globalPathConstraint); j < Math.min(m, i + globalPathConstraint); j++) {   // consider five different moves.
				double cost = getDistance(test[i], reference[j]);
				double d1 = cost + DTW[i - 1][j];
				double d2 = cost + DTW[i][j - 1];
				double d3 = 2 * cost + DTW[i - 1][j - 1];
				double d4 = Double.MAX_VALUE;
				if (j > 1)
					d4 = 3 * cost + DTW[i - 1][j - 2];
				double d5 = Double.MAX_VALUE;
				if (i > 1)
					d5 = 3 * cost + DTW[i - 2][j - 1];

				DTW[i][j] = getMin(d1, d2, d3, d4, d5);
			}
		}

		return DTW[n - 1][m - 1] / (m + n);
	}

	public double getMin(double... num) {
		double min = Double.MAX_VALUE;
		for (int i = 0; i < num.length; i++) {
			if (num[i] < min)
				min = num[i];
		}

		return min;
	}

	/**
	 * Calculate the distance between two feature vectors. The two feature
	 * vector have the same dimension. When calculating such Euclidean distance,
	 * we need to take variance into account.
	 *
	 * @param vec1 the first feature vector
	 * @param vec2 the second feature vector
	 * @return Normalized Euclidean distance (or in manhatan distance,
	 * the covariance matrix is diagonal matrix, where each diagonal
	 * entry is the variance for that dimension)
	 */
	private double getDistance(double[] vec1, double[] vec2) {
		double distance = 0.0;
		for (int i = 0; i < vec1.length; i++)
			distance += (vec1[i] - vec2[i]) * (vec1[i] - vec2[i]) / variance[i];

		return Math.sqrt(distance);
	}
}
