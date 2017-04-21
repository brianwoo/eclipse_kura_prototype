Service Stop (POST)
http://localhost:8080/obs-kura-rest/service/124/stop?gateway=08:00:27:52:DC:FC&requester=brianRequester&requestId=brianId123


Service Start (POST)
http://localhost:8080/obs-kura-rest/service/124/start?gateway=08:00:27:52:DC:FC&requester=brianRequester&requestId=brianId123


Service Deploy (Reg string) (POST)
http://localhost:8080/obs-kura-rest/deploy?gateway=08:00:27:52:DC:FC&pkgUrl=https://github.com/brianwoo/eclipse_kura_prototype/raw/master/com.obs.bwoo.temperature.reader/resources/obsBwooTemperatureReader_reg_strings.dp&pkgName=obsTemperatureReader&pkgVersion=1.0.2&requester=brianRequester&requestId=brianId123


Service Deploy (JSON) (POST)
http://localhost:8080/obs-kura-rest/deploy?gateway=08:00:27:52:DC:FC&pkgUrl=https://github.com/brianwoo/eclipse_kura_prototype/raw/master/com.obs.bwoo.temperature.reader/resources/obsBwooTemperatureReader_json.dp&pkgName=obsTemperatureReader&pkgVersion=1.0.3&requester=brianRequester&requestId=brianId123


Get List of Services and Statuses (GET)
http://localhost:8080/obs-kura-rest/service?gateway=08:00:27:52:DC:FC&requester=brianRequester&requestId=brianId123
