<!DOCTYPE html>
<!--[if lt IE 7]> <html lang="en" ng-app="myApp" class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]> <html lang="en" ng-app="myApp" class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]> <html lang="en" ng-app="myApp" class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="fa" ng-app="myApp" class="no-js"> <!--<![endif]-->
<head>
    <base href="/"/>
    <%--<meta http-equiv="Content-Type" content="text/html; charset=utf-8">--%>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="enamad" content="209154864"/>
    <title>utadoc.com | UtaDoc</title>
    <meta charset="UTF-8" name="description"
          content="">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta property="og:title" content="utadoc.com"/>
    <meta property="og:type" content="website"/>
    <meta property="og:url" content="http://utadoc.com"/>
    <meta property="og:image" content="http://utadoc.com/resources/images/logo.png"/>
    <meta property="og:image:type" content="image/png"/>
    <meta property="og:description"
          content="The first Document Authentication and Timestamping System (Utadoc) for different kinds of digital contents such as documents, plans, ideas as well as business, scientific and artistic works in the immutable platform of blockchain"/>
    <meta property="og:locale" content="en_US"/>
    <meta name="google-site-verification" content="PRxZfT9Z3nTX5NpSI4x4VYal2yUNCIYcyVPeNSfpjrg"/>
    <meta property="og:site_name" content="utadoc"/>
    <script type="application/ld+json">
    {
      "@context":"http://schema.org",
      "@type": "WebSite",
      "name": "utadoc.com",
      "alternateName": "utadoc",
      "url": "http://utadoc.com",
      "logo":"http://utadoc.com/resources/images/logo.png",
      "description": "The first Document Authentication and Timestamping System (Utadoc) for different kinds of digital contents such as documents, plans, ideas as well as business, scientific and artistic works in the immutable platform of blockchain",
      "contactPoint": [
        { "@type": "ContactPoint",
          "contactType": "customer support",
          "availableLanguage": "English, Russian"
        }
      ]
    }

    </script>
    <!--[if IE 9]>
    <script type="text/javascript">
        window.isIE9 = true;
    </script>
    <![endif]-->


    <link rel="stylesheet" href="/resources/bower_components/html5-boilerplate/dist/css/normalize.css">
    <link rel="stylesheet" href="/resources/bower_components/html5-boilerplate/dist/css/main.css">
    <link rel="stylesheet" href="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/bower_components/angular-bootstrap/ui-bootstrap-csp.css">
    <link rel="stylesheet" href="/resources/bower_components/angular-material/angular-material.min.css">
    <link rel="stylesheet" href="/resources/bower_components/angular-growl-v2/build/angular-growl.min.css">
    <link rel="stylesheet" href="/resources/bower_components/angular-material-data-table/dist/md-data-table.min.css">
    <link href="/resources/bower_components/v-accordion/dist/v-accordion.min.css" rel="stylesheet"/>

    <%--<script src="/resources/bower_components/angular-touch/angular-touch.js?v=7"></script>--%>
    <%--<script src="/resources/bower_components/angular-animate/angular-animate.js?v=7"></script>--%>
    <link href="/resources/images/logo-condensed.png" rel="icon" type="image/x-icon"/>

</head>
<body ng-cloak flex="100" style="background-color: black;">
<!--[if lt IE 10]>
<div style="display: block;width:100%;height:500px;font-family: Consolas; font-size: 18px; color: #2962ff; font-weight: bold;text-align: center;background: #D6D6D6;">
    <span style="display:inline-block; margin: 10% auto;padding: 50px;">You are using and outdated browser. Please upgrade your browser or use another browser </br>
        (Preferably Google Chrome, Mozilla Firefox, Edge or Safari)</span>
</div>
<![endif]-->
<div growl></div>
<%--<div header></div>--%>
<div ng-view layout="column" style="min-height: 68%;" layout-align="center center" autoscroll="true"></div>
<div footer style="position: relative;bottom: 0;"></div>

<%--head--%>
<!--[if IE]>
<script src="/resources/dependencies/typedarray.js?v=7"></script>
<script src="/resources/dependencies/ie-support.js?v=7"></script>
<!--<![endif]-->
<!--[if gt IE 9]><!-->
<%--<link rel="stylesheet" href="/resources/dist/styles.min.css?">--%>
<link rel="stylesheet" href="/resources/app/app.css?v=7">
<link rel="stylesheet" href="/resources/font/MyriadPro/myriad.css">
<link rel="stylesheet" href="/resources/bower_components/adm-dtp/dist/ADM-dateTimePicker.min.css"/>
<%--<link rel="stylesheet" href="/resources/font/vazir-fd/vazir-fd.css?">--%>

<%--<link rel="stylesheet" href="/resources/app/app.css">--%>
<%--<link rel="stylesheet" href="/resources/bower_components/vazir-font/dist/font-face.css">--%>
<%--<link rel="stylesheet" href="/resources/font/webyekan/webyekan.css">--%>
<link rel="stylesheet" href="/resources/bower_components/material-design-icons/iconfont/material-icons.css">
<%--<link rel="stylesheet" href="/resources/bower_components/angular-loading-bar/build/loading-bar.min.css">--%>

<script src="/resources/bower_components/lodash/dist/lodash.min.js?v=7"></script>

<script src="/resources/bower_components/html5-boilerplate/dist/js/vendor/modernizr-2.8.3.min.js?v=7"></script>
<script src="/resources/bower_components/angular/angular.min.js?v=7" charset="UTF-8"></script>
<%--<script src="/resources/dist/scripts.header.js?v=7" charset="UTF-8"></script>--%>
<!--<![endif]-->
<script src="https://apis.google.com/js/client.js?v=7"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js?v=7"></script>
<script>
	var isDlgOpen = false;
	function auth(func) {
		var config = {
			'client_id': '715334869497-fv4sdl15v0m4qhnmunmn4kd03hiec249.apps.googleusercontent.com',
			'scope': 'https://www.googleapis.com/auth/contacts.readonly'
		};
		gapi.auth.authorize(config, function () {
			fetch(gapi.auth.getToken(), func);
		});
	}
	function fetch(token, func) {
		var access_token = token["access_token"];
		token['g-oauth-window'] = null;
		$.ajax({
			url: 'https://www.google.com/m8/feeds/contacts/default/full?alt=json',
			dataType: 'jsonp',
			data: token
		}).done(function (data) {
			var list = data["feed"]["entry"];
			var emails = {};
			var photos = {};
			for (var i = 0; i < list.length; i++) {
				var item = list[i];
				if (item["gd$email"].length > 0 && item["link"].length > 0) {
					emails[item["gd$email"][0]["address"]] = item["gd$email"][0]["address"]
					photos[item["gd$email"][0]["address"]] = item["link"][0]["href"] + "?access_token=" + access_token;
				}
			}
			func(emails, photos);
		});
	}
</script>
<script type="text/javascript">
	if (!FileReader.prototype.readAsBinaryString) {
		FileReader.prototype.readAsBinaryString = function (fileData) {
			var binary = "";
			var pt = this;
			var reader = new FileReader();
			reader.onload = function (e) {
				var bytes = new Uint8Array(reader.result);
				var length = bytes.byteLength;
				for (var i = 0; i < length; i++) {
					binary += String.fromCharCode(bytes[i]);
				}
				//pt.result  - readonly so assign binary
				pt.content = binary;
				pt.onload();
			}
			reader.readAsArrayBuffer(fileData);
		}
	}
	var _socialAuthMode =${socialAuthMode == null? 'false': socialAuthMode};
	var _socialAuthSuccess =${socialAuthSuccess == null? 'false': socialAuthSuccess};
	var _emails =${emails == null? '{}':emails};
	var _photos =${photos == null ? '{}':photos};
	var _recoverMode =${recoverMode == null ? 'false' : recoverMode};
	var _recoverExpired =${recoverExpired == null ? 'true' : recoverExpired};
	var _recoverMail = "${recoverMail}";
	var _recoverHash = "${recoverHash}";
	var _paymentMode = "${paymentMode}";
	var _status = "${status}";
	var _statusString = "${statusString}";
	var _refId = "${refId}";
</script>
<%--endhead--%>

<script src="/resources/bower_components/angular-aria/angular-aria.min.js?v=7"></script>
<script src="/resources/bower_components/angular-animate/angular-animate.js?v=7"></script>
<script src="/resources/bower_components/angular-material/angular-material.min.js?v=7"></script>

<script src="/resources/bower_components/angular-bootstrap/ui-bootstrap.min.js?v=7"></script>
<script src="/resources/bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js?v=7"></script>

<script src="/resources/bower_components/angular-route/angular-route.min.js?v=7"></script>

<script src="/resources/bower_components/angular-growl-v2/build/angular-growl.min.js?v=7"></script>

<script src="/resources/bower_components/ng-file-upload-shim/ng-file-upload-shim.min.js?v=7"></script>
<script src="/resources/bower_components/ng-file-upload/ng-file-upload.min.js?v=7"></script>
<script src="/resources/bower_components/angular-local-storage/dist/angular-local-storage.min.js?v=7"></script>
<script src="/resources/bower_components/angular-material-data-table/dist/md-data-table.min.js?v=7"></script>
<%--angular-qrcode--%>
<script src="/resources/dependencies/qrcode.js?v=7"></script>
<script src="/resources/dependencies/qrcode_UTF8.js?v=7"></script>
<script src="/resources/bower_components/angular-qrcode/angular-qrcode.js?v=7"></script>
<script src="/resources/bower_components/angular-qrcode/angular-qrcode.js?v=7"></script>

<script src="/resources/bower_components/moment/min/moment.min.js?v=7"></script>
<script src="/resources/bower_components/moment/min/moment-with-locales.min.js?v=7"></script>
<script src="/resources/bower_components/moment-jalaali/build/moment-jalaali.js?v=7"></script>
<script src="/resources/bower_components/angular-translate/angular-translate.min.js?v=7"></script>
<script src="/resources/bower_components/bluebird/js/browser/bluebird.min.js?v=7"></script>
<script src="/resources/bower_components/Jdenticon/dist/jdenticon.min.js?v=7"></script>
<%--<script src="/resources/bower_components/identicon.js?v=7/identicon.js?v=7"></script>--%>

<script src="/resources/bower_components/angular-sanitize/angular-sanitize.min.js?v=7"></script>
<script src="/resources/bower_components/videogular/videogular.min.js?v=7"></script>
<script src="/resources/bower_components/videogular-controls/vg-controls.min.js?v=7"></script>
<script src="/resources/bower_components/videogular-overlay-play/vg-overlay-play.min.js?v=7"></script>
<script src="/resources/bower_components/videogular-poster/vg-poster.min.js?v=7"></script>
<script src="/resources/bower_components/videogular-buffering/vg-buffering.min.js?v=7"></script>
<%--<script src="/resources/bower_components/particles.js?v=7/particles.min.js?v=7"></script>--%>

<script src="/resources/bower_components/angular-messages/angular-messages.min.js?v=7"></script>
<%--<script src="http://maps.google.com/maps/api/js"></script>--%>

<script src="/resources/app/app.js?v=7" charset="UTF-8"></script>
<%--<script src="/resources/dist/app.min.js?v=7"></script>--%>

<script src="/resources/app/translate.js?v=4v=2" charset="UTF-8"></script>
<script src="/resources/app/home/homeController.js?v=7" charset="UTF-8"></script>

<script src="/resources/app/header/headerDirective.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/header/stickyHeaderDirective.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/header/headerController.js?v=7" charset="UTF-8"></script>

<script src="/resources/app/signup/signupController.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/signin/signinController.js?v=7" charset="UTF-8"></script>
<%--<script src="/resources/dist/bundle2.js?v=7" charset="UTF-8"></script>--%>
<script src="/resources/dist/bluebird-storage.js?v=7" charset="UTF-8"></script>

<script src="/resources/app/recover/recoverController.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/contact/contactController.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/profile/profileController.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/transaction/transactionListController.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/transaction/transactionCreateController.js?v=4v=2" charset="UTF-8"></script>
<script src="/resources/app/transaction/transactionSummaryController.js?v=4v=2" charset="UTF-8"></script>
<script src="/resources/app/transaction/transactionDetailController.js?v=4v=2" charset="UTF-8"></script>
<%--<script src="/resources/app/transaction/transactionValidateController.js?v=7" charset="UTF-8"></script>--%>
<script src="/resources/app/auth/authController.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/forgetPassword/forgetPasswordController.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/errorToast/errorToastController.js?v=7" charset="UTF-8"></script>
<%--<script src="/resources/dist/bundle1.js?v=7" charset="UTF-8"></script>--%>
<script src="/resources/app/transaction/transactionValidateController.js?v=7" charset="UTF-8"></script>


<script src="/resources/app/footer/footerDirective.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/footer/footerController.js?v=7" charset="UTF-8"></script>

<script src="/resources/app/upload/uploadDirective.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/upload/uploadController.js?v=4v=2" charset="UTF-8"></script>
<script src="/resources/app/upload/uploadService.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/upload/validateUploadDirective.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/upload/validateUploadController.js?v=7" charset="UTF-8"></script>

<script src="/resources/app/payment/paymentController.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/payment/paymentResultController.js?v=7" charset="UTF-8"></script>
<%--<script src="/resources/dist/footer-upload-payment.js?v=7" charset="UTF-8"></script>--%>

<script src="/resources/app/factories/UtilsFactory.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/factories/StorageFactory.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/factories/UserFactory.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/factories/EmailContactFactory.js?v=7" charset="UTF-8"></script>
<script src="/resources/app/factories/BrowserFactory.js?v=7" charset="UTF-8"></script>

<script src="/resources/dist/scripts.footer.js?v=7" charset="UTF-8"></script>

<script src="/resources/bower_components/adm-dtp/dist/ADM-dateTimePicker.min.js?v=7" charset="UTF-8"></script>
<script src="/resources/bower_components/v-accordion/dist/v-accordion.min.js?v=7"></script>
<script src="/resources/bower_components/angular-sticky/dist/angular-sticky.min.js?v=7"></script>

<%--<script src="/resources/dependencies/asmcrypto.js?v=7"></script>--%>

<%--<script src="/resources/app/components/version/version.js?v=7"></script>--%>
<%--<script src="/resources/app/components/version/version-directive.js?v=7"></script>--%>
<%--<script src="/resources/app/components/version/interpolate-filter.js?v=7"></script>--%>

<%--<script src="/resources/bower_components/ngmap/build/scripts/ng-map.min.js?v=7"></script>--%>
<%--<script src="/resources/dependencies/maps.google.js?v=7" charset="UTF-8"></script>--%>
<%--<script src="http://maps.google.com/maps/api/js?key=AIzaSyDBQBRNCh5cTcXvINWhAYiCevW8ILiPdHA"></script>--%>
<%--<script src="/resources/bower_components/angular-loading-bar/build/loading-bar.min.js?v=7"></script>--%>
<script src="/resources/js/sockjs.js?v=7"></script>
<script src="/resources/js/stomp.js?v=7"></script>
<script>
//	var p = document.getElementById("header-profile");
//	p.onclick = function(event) { alert("moot!"); };
$( "#header-profile" ).click(function() {
	alert( "Handler for .click() called." );
});
	$(function () {
		var width = $(window).width();
        if (width <= 961) return;
		var shrinkHeader = 100;
		$(window).scroll(function () {
			var scroll = getCurrentScroll();
			if (scroll >= shrinkHeader) {
				$('.header').addClass('shrink');
				$('.logo-shrinked').show();
				$('.logo-normal').hide();
			}
			else {
				$('.header').removeClass('shrink');
				$('.logo-shrinked').hide();
				$('.logo-normal').show();
			}
		});
		function getCurrentScroll() {
			return window.pageYOffset || document.documentElement.scrollTop;
		}
	});

</script>
</body>
</html>
