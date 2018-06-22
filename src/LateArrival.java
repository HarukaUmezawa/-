import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class LateArrival{
	public static final int HOME_A_Station = 0;
	public static final int H_TIME = 1;
	public static final int M_TIME = 60;
	public static final int ARRIVAL_H_TIME = 8;
	public static final int ARRIVAL_M_TIME = 59;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Map<Integer,Integer> map = new HashMap<>();
		int hTime = 0; // 通勤時間(時)
		int mTime = 0; // 通勤時間(分)
		int departureHTime = 0; //電車の発車時刻(時)
		int departureMTime = 0; //電車の発車時刻(分)
		int homeHTime = 0; //自宅を出発する時刻(時)
		int homeMTime = 0; //自宅を出発する時刻(分)

		/** 通勤時間 */
		String CommutingTimes = sc.nextLine();
		String[] CommutingTime = CommutingTimes.split("[\\s]");

		/** 電車の発車時刻 */
		int N = sc.nextInt();
		for (int i = 0; i < N; i++) {
	    	int hour = sc.nextInt();
	    	int min = sc.nextInt();
	    	map.put(hour, min);
		}

		/** A駅から会社までの通勤時間 */
		for(int m = 1; m < CommutingTime.length; m++){
			mTime += Integer.parseInt(CommutingTime[m]);
			while(mTime >= M_TIME){
				mTime -= M_TIME;
				hTime += H_TIME;
			}
		}

		/** 発車時刻に通勤時間を加算
		 *  8:59までに会社へ到着する電車の発車時刻 */
		for(Entry<Integer, Integer> entry : map.entrySet()){
			int ht = entry.getKey() + hTime;
			int mt = entry.getValue() + mTime;
			while(mt >= M_TIME){
				mt -= M_TIME;
				ht += H_TIME;
			}
			if( ht == ARRIVAL_H_TIME && mt <= ARRIVAL_M_TIME ){
				if( mt > departureMTime ) {
					departureHTime = entry.getKey();
					departureMTime = entry.getValue();
				}
			}
		}

		/** 自宅を出発する時刻 */
		homeMTime = departureMTime - Integer.parseInt(CommutingTime[HOME_A_Station]);
		if ( homeMTime < 0 ) {
			homeMTime += M_TIME;
			homeHTime -= H_TIME;
		}

		System.out.println(String.format("%02d", homeHTime) + ":" + String.format("%02d", homeMTime));
		sc.close();
	}
}