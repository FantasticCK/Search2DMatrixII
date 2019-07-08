package com.CK;

public class Main {

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 3, 4, 5};
        int[] b = new int[]{6, 7, 8, 9, 10};
        int[] c = new int[]{11, 12, 13, 14, 15};
        int[] d = new int[]{16, 17, 18, 19, 20};
        int[] e = new int[]{21, 22, 23, 24, 25};


        int[][] matrix = new int[5][5];
        matrix[0] = a;
        matrix[1] = b;
        matrix[2] = c;
        matrix[3] = d;
        matrix[4] = e;
        Solution solution = new Solution();
        System.out.println(solution.searchMatrix(matrix, 23));
    }
}

class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) return false;
        int top = 0, bottom = matrix.length - 1, left = 0, right = matrix[0].length - 1, tbMid, lrMid;
        return searchMatrixWithGivenArea(matrix, target, top, bottom, left, right);
    }

    private boolean searchMatrixWithGivenArea(int[][] matrix, int target, int top, int bottom, int left, int right) {
        if (matrix.length == 0 || matrix[0].length == 0) return false;
        int tbMid, lrMid;
        boolean leftHalfSearch = false, topHalfSearch = false, bottomHalfSearch = false, rightHalfSearch = false;
        while (top + 1 < bottom && left + 1 < right) {
            tbMid = (top + bottom) / 2;
            lrMid = (left + right) / 2;
            int midVal = matrix[tbMid][lrMid];
            if (midVal == target) return true;
            else if (midVal > target) {
                if(searchMatrixWithGivenArea(matrix, target, top, bottom, left, lrMid)) right = lrMid;
                else bottom = tbMid;
            } else {
                if(searchMatrixWithGivenArea(matrix, target, top, bottom, lrMid, right)) left = lrMid;
                else top = tbMid;
            }
        }
        boolean searchResult = searchTargetInGivenAreaOfMatrix(matrix, target, top, bottom, left, right);
        return searchResult;
    }

    private boolean searchTargetInGivenAreaOfMatrix(int[][] matrix, int target, int top, int bottom, int left, int right) {
        for (int i = top; i <= bottom; i++) {
            for (int j = left; j <= right; j++) {
                if (matrix[i][j] == target) return true;
            }
        }
        return false;
    }
}


class Solution2 {
    private boolean binarySearch(int[][] matrix, int target, int start, boolean vertical) {
        int lo = start;
        int hi = vertical ? matrix[0].length-1 : matrix.length-1;

        while (hi >= lo) {
            int mid = (lo + hi)/2;
            if (vertical) { // searching a column
                if (matrix[start][mid] < target) {
                    lo = mid + 1;
                } else if (matrix[start][mid] > target) {
                    hi = mid - 1;
                } else {
                    return true;
                }
            } else { // searching a row
                if (matrix[mid][start] < target) {
                    lo = mid + 1;
                } else if (matrix[mid][start] > target) {
                    hi = mid - 1;
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        // an empty matrix obviously does not contain `target`
        if (matrix == null || matrix.length == 0) {
            return false;
        }

        // iterate over matrix diagonals
        int shorterDim = Math.min(matrix.length, matrix[0].length);
        for (int i = 0; i < shorterDim; i++) {
            boolean verticalFound = binarySearch(matrix, target, i, true);
            boolean horizontalFound = binarySearch(matrix, target, i, false);
            if (verticalFound || horizontalFound) {
                return true;
            }
        }

        return false;
    }
}
