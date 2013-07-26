// Geolocation是W3C 中新添加API 规范，Geoloaction API的作用就是通过浏览器获取用户的地理位置。

if(navigator.geolocation) {
    
    navigator.geolocation.getCurrentPosition(
        // callback with position info
        function(position) {
            var lat = position.coords.latitude;
            var lon = position.coords.longitude;
        }

        // callback when error happens
        function(error) {
            alert(ouch);
        }
    );
}

navigator.geolocation对象有以下三种方法：
    1. 获取当前地理位置：
        navigator.geolocation.getCurrentPosition(
                success_callback_function, 
                error_callback_function, 
                position_options)

   2. 持续获取地理位置：
        navigator.geolocation.watchPosition(
            success_callback_function, 
            error_callback_function, 
            position_options)

   3. 清除持续获取地理位置事件：
        navigator.geolocation.clearWatch(watch_position_id)

        参数:
            success_callback_function
                为成功之后处理的函数，
            error_callback_function
                为失败之后返回的处理函数，
            position_options
                是配置项。

                position_options由JSON格式传入：
                    enableHighAccuracy： true/false，默认指为 true，
                        它将告诉浏览器是否启用高精度设备，(GPS>AGPS>Mobile>IP ???)
                        在这种情况下，浏览器会尽可能地进行更为精确的查询，
                        简单地说，如果用户有可用的GPS 设备，会返回 GPS 设备的查询结果，IP 是最后的选择，
                        对于移动设备来说，网络接入点(基站)或许成为另一个选择，对此我还没有完全了解，

                    maximumAge：单位毫秒，告诉设备缓存时间，主要用于设备的省电或者节省带宽方面。
                    timeout：单位毫秒，超时事件，获取位置信息时超出设定的这个时长，
                        将会触发错误，捕获错误的函数将被调用，并且错误码指向TIMEOUT。

// http://www.cnblogs.com/jetwu/archive/2011/10/27/2226092.html

// map.google.com获取在android上获取location的代码
IMa=function(a,b){if(!a.F){var c=m
navigator.mockGeolocation?c=navigator.mockGeolocation:navigator.geolocation&&(c=navigator.geolocation)
a.F=c}a.kA=Cp(a)
b.tick("mlwp0")
var d=C(a.K,a),e=C(a.J,a),f=b.Jf()
a.G=a.F.watchPosition(C(function(a){d(a,f)
this.kA.Lb()&&f.done()
jt(this)}, a),C(function(a){e(a,f)
this.kA.Lb()&&(f.done(),jt(this))}, a),{maximumAge:3E5,timeout:1E4})}

