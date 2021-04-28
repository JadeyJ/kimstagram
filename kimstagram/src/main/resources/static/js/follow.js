function follow(check, userId, idx) {
	let url = "/follow/" + userId;
	if (check) {
		fetch(url, {
			method: "POST",
		}).then(function(res) {
			return res.text();
		}).then(function(res) {
			if (res == "ok") {
				let follow_item_el = document.querySelector("#follow_item_" + idx);
				follow_item_el.innerHTML = `<button onClick="follow(false, ${userId}, ${idx})" class="following_btn">팔로잉</button>`;
			}
		});
	} else {
		fetch(url, {
			method: "DELETE",
		}).then(function(res) {
			return res.text();
		}).then(function(res) {
			if (res == "ok") {
				let follow_item_el = document.querySelector("#follow_item_" + idx);
				follow_item_el.innerHTML = `<button onClick="follow(true, ${userId}, ${idx})" class="follow_btn">팔로우</button>`;
			}
		});

	}
}