# Comparision Results
This an initial comparision results and notes based on my preliminary findings. I intend to create a web service to post user results and make a full detailed comparision. 

## Testing Environment

| Type | Details
|:---|:---|
| Test Date | 24/06/2015 |
| Device  | Motorola Moto G 1st Generation (2013)  |
| Android Version | Lollipop 5.0.2 |
| Android API | API 21 |
| RAM | 1 GB |
| Processor | 1.2 Ghz |

## Performance Comparision Chart
*Screenshots taken from application*

| No. of test rows | Type | Comparision |
| :--- | :--- | :--- |
| 1,000 | Insert |<img src="https://cloud.githubusercontent.com/assets/5122822/8336445/476836a6-1ac2-11e5-8b91-7eec1de84672.JPG" width="300" height="450"/> |
|  | Read |<img src="https://cloud.githubusercontent.com/assets/5122822/8336447/4bef01dc-1ac2-11e5-90ba-a963e9b3e251.JPG" width="300" height="450"/> |
|  | Update |<img src="https://cloud.githubusercontent.com/assets/5122822/8336450/506c0aac-1ac2-11e5-98ae-2d2484f60612.JPG" width="300" height="450"/> |
|  | Delete |<img src="https://cloud.githubusercontent.com/assets/5122822/8336451/53214938-1ac2-11e5-84d0-cb402d6a97ad.JPG" width="300" height="450"/> |

## Notes
* Till now, I have not yet got a good result in case of Couchbase Lite, as you can see is painfully slow in all respects. Maybe I am doing something wrong; if so please raise an issue.
* If you find your *beloved* db's performance is not what is expected, feel free to submit a PR. Also, once you get the release apk you could try it out in your devices and report!
* Also, if you find any result way out of proportions, suggest a workaround! :)
