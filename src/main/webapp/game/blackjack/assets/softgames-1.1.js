function sg_exists() {
	return false
}

function SG_initAPI(e) {
	return SG.initLangs(e)
}

function SG_hideAddressBar() {
	SG.hideAddressBar()
}

function SG_OrientationHandler(e, t) {
	if (typeof e != "undefined" && e != null) SG.setOrientationHandler(e);
	if (typeof t != "undefined" && t != null) SG.setResizeHandler(t)
}
var SG_Lang = "en";
var SG = {
	loaded: false,
	debug: false,
	lang: "en",
	gameJS: [],
	d: document,
	loadScrnTimer: 10,
	boot: function() {
		SG.initLangs(window.gameLangs);
		if (sg_exists()) {
			window.softgames.gameInitCallback = SG.startGame;
			window.softgames.getReady()
		} else {
			SG.startGame()
		}
	},
	startGame: function() {
		if (SG.loaded) return;
		SG.loaded = true;
		SG.showSpinner();
		if (typeof window.gamePreLoader == "function") {
			window.gamePreLoader()
		}
		SG.loadJsFiles(window.gameJS, function() {
			SG.hideLoadScrn();
			if (window.gameOnLoadScript) {
				eval(window.gameOnLoadScript)
			}
		})
	},
	showSpinner: function() {},
	showLoadScrn: function() {
		var e = SG.d.createElement("div");
		e.setAttribute("id", "sg-spinner");
		var t = SG.d.createElement("div");
		t.setAttribute("id", "sg-loadscrn");
		t.appendChild(e);
		if (window.location.href.indexOf("adultcontent") != -1) {
			var n = SG.d.createElement("div");
			n.setAttribute("id", "sg-loadtext");
			n.innerHTML = "One moment please...<br>Your site is almost loaded!";
			t.appendChild(n)
		}
		var r = function() {
			var e = SG.d.getElementsByTagName("body")[0];
			if (typeof e != "undefined") {
				if (SG.d.getElementById("sg-loadscrn") == null) {
					SG.debug && console.log("show load-screen: complete");
					e.appendChild(t)
				}
				SG.loadVoyager()
			} else {
				if (SG.debug) console.log("show load-screen: body-tag not ready. retrying in " + SG.loadScrnTimer + "ms");
				setTimeout(r, SG.loadScrnTimer)
			}
		};
		r()
	},
	hideLoadScrn: function() {
		var e = SG.d.getElementById("sg-loadscrn");
		if (e) e.parentNode.removeChild(e)
	},
	loadJsFiles: function(e, t) {
		var n = SG.d.getElementsByTagName("head")[0] || document.documentElement,
			r = [],
			i = e.length;
		if (e.length > 0) {
			var s = document.createElement("script"),
				r = false;
			s.type = "text/javascript";
			s.src = e[0];
			e.shift();
			var o = false;
			s.onreadystatechange = s.onload = function() {
				if (!o && (!this.readyState || this.readyState === "loaded" || this.readyState === "complete")) {
					o = true;
					s.onload = s.onreadystatechange = null;
					if (n && s.parentNode) {
						n.removeChild(s)
					}
					SG.loadJsFiles(e, t)
				}
			};
			n.insertBefore(s, n.firstChild);
			if (SG.debug) console.log("loading " + s.src + ", " + e.length + " files left.")
		} else if (typeof t == "function") {
			if (SG.debug) console.log("calling callback: " + t);
			t()
		}
	},
	loadCSSFiles: function(e) {
		if (e.length == 0) return;
		var t = SG.d.getElementsByTagName("head")[0] || document.documentElement;
		for (var n = 0; n < e.length; n++) {
			var r = document.createElement("link");
			r.rel = "stylesheet";
			r.type = "text/css";
			r.href = e[n];
			t.insertBefore(r, t.firstChild)
		}
	},
	trigger: function(e, t) {
		if (!sg_exists()) return false;
		switch (e.type) {
			case "start":
				d = {
					type: window.softgames.eventStartingGame
				};
				break;
			case "levelUp":
				d = {
					type: window.softgames.eventLevelUp,
					level: e.level
				};
				break;
			case "gameOver":
				d = {
					type: window.softgames.eventGameOver,
					score: e.score
				};
				break;
			case "gameCompleted":
				d = {
					type: window.softgames.eventGameCompleted,
					score: e.score
				};

				break;
			case "gamePause":
				d = {
					type: window.softgames.eventGamePause,
					state: e.state
				};
				break;
			case "gameRestart":
				d = {
					type: window.softgames.eventGameRestart
				};
				break;
			case "selectLevel":
				d = {
					type: window.softgames.eventSelectLevel,
					level: e.level
				};
				break;
			case "selectMainMenu":
				d = {
					type: window.softgames.eventSelectMainMenu
				};
				break;
			case "setSound":
				d = {
					type: window.softgames.eventSound,
					state: e.state
				};
				break
		}
		window.softgames.trigger(d, t);
		return true
	},
	initLangs: function(e) {
		var t = typeof SG_getLang == "function" ? SG_getLang() : "en";
		var n = Object.prototype.toString.call(e).toLowerCase() == "[object array]";
		if (n && e.indexOf(t) >= 0) SG.lang = t;
		SG_Lang = SG.lang;
		return SG.lang
	},
	getLang: function() {
		return SG.lang
	},
	setOrientationHandler: function(e) {
		if (sg_exists()) window.softgames.changeScreenOrientation = e
	},
	setResizeHandler: function(e) {
		if (sg_exists()) window.softgames.changeScreenSize = e
	},
	hideAddressBar: function() {
		setTimeout("window.scrollTo(0, 1)", 10)
	},
	loadVoyager: function() {
		var e = document.createElement("script");
		e.type = "text/javascript";
		e.async = true;
		var t = Math.floor(Math.random() * 1e8 + 1);
		SG.boot();
		var n = document.getElementsByTagName("script")[0];
		n.parentNode.insertBefore(e, n)
	},
	redirectToPortal: function() {
		Play68.goHome()
	},
	detectPortalUrl: function() {
		var e = softgames.back_url;
		if (typeof e !== "string") e = softgames.subplatform;
		if (typeof e !== "string") e = "http://m.softgames.de";
		else e = "http://" + e;
		SG.portalURL = e
	},
	getLogoUrl: function(e) {
		return "images/play68_logo.png"
	}
};
SG.showLoadScrn()