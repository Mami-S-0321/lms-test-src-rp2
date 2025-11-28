package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	/*@AfterAll
	static void after() {
		closeDriver();
	}*/

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// TODO ここに追加
		//画面遷移
		goTo("http://localhost:8080/lms");

		//タイトルの値とログインボタンの値が一致しているかどうか
		String title = webDriver.getTitle();
		assertEquals("ログイン | LMS", title);

		//ログイン画面のエビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
		//ログインの際に必要な値取得
		final WebElement loginId = webDriver.findElement(By.name("loginId"));
		final WebElement password = webDriver.findElement(By.name("password"));
		final WebElement login = webDriver.findElement(By.className("btn"));

		//ログインの際に入力する値
		loginId.clear();
		loginId.sendKeys("StudentAA01");
		password.clear();
		password.sendKeys("StudentAA0");
		login.click();

		//ログインできているかどうかの確認
		final String loginUser = webDriver
				.findElement(
						By.cssSelector("#nav-content > ul.nav.navbar-nav.navbar-right > li:nth-child(2) > a > small"))
				.getText();
		assertEquals("ようこそ受講生ＡＡ１さん", loginUser);

		//ログイン後の表示待ち
		visibilityTimeout(By.className("btn"), 10);
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() throws InterruptedException {
		// TODO ここに追加
		//上部メニューの「勤怠」リンクを押下
		final WebElement Attendance = webDriver.findElement(
				By.cssSelector("#nav-content > ul:nth-child(1) > li:nth-child(3) > a"));
		Attendance.click();

		//アラートの受け入れ
		webDriver.switchTo().alert().accept();

		//画面遷移待ち
		Thread.sleep(5000);

		//勤怠管理画面に遷移できたかどうか
		final String AttendanceManagement = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/attendance/detail", AttendanceManagement);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() throws InterruptedException {
		// TODO ここに追加
		//「勤怠情報を直接編集する」リンクを押下
		final WebElement DirectEditing = webDriver.findElement(
				By.cssSelector("#main > div.well.well-lg.p10.mb10 > p > a"));
		DirectEditing.click();

		//画面遷移待ち
		Thread.sleep(5000);

		//勤怠情報直接変更画面に遷移できたかどうか
		final String DirectEditingUrl = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/attendance/update", DirectEditingUrl);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() throws InterruptedException {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("1000");

		//10/13の出退勤(時)を空白,出退勤(分)を00分
		WebElement workHourSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[4]/select"));
		WebElement workMinuteSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[6]/select"));
		WebElement clockOutHourSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[7]/select"));
		WebElement clockOutMinuteSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[9]/select"));
		Select workHour = new Select(workHourSelect);
		Select workMinute = new Select(workMinuteSelect);
		Select clockOutHour = new Select(clockOutHourSelect);
		Select clockOutMinute = new Select(clockOutMinuteSelect);
		workHour.selectByIndex(0);
		workMinute.selectByIndex(1);
		clockOutHour.selectByIndex(0);
		clockOutMinute.selectByIndex(1);

		getEvidence(new Object() {
		}, "1");

		//更新ボタン押下
		final WebElement update = webDriver
				.findElement(
						By.cssSelector("#main > div > div > form > div > input"));
		update.click();

		//アラート受け入れ
		Alert Alert = webDriver.switchTo().alert();
		Alert.accept();

		//インナー要素のスクロールを指定位置へ
		final WebElement scroll = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody"));
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + 300;", scroll);

		//エラーが出ているかどうか
		final String workError = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/ul/li[1]/span"))
				.getText();
		assertEquals("* 出勤時間が正しく入力されていません。", workError);
		final String clockOutError = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/ul/li[2]/span"))
				.getText();
		assertEquals("* 退勤時間が正しく入力されていません。", clockOutError);

		getEvidence(new Object() {
		}, "2");

		//リセット用スクロール
		WebElement workHourSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[4]/select"));
		WebElement workMinuteSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[6]/select"));
		WebElement clockOutHourSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[7]/select"));
		WebElement clockOutMinuteSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[9]/select"));
		Select workHourReset = new Select(workHourSelectReset);
		Select workMinuteReset = new Select(workMinuteSelectReset);
		Select clockOutHourReset = new Select(clockOutHourSelectReset);
		Select clockOutMinuteReset = new Select(clockOutMinuteSelectReset);
		workHourReset.selectByIndex(0);
		workMinuteReset.selectByIndex(0);
		clockOutHourReset.selectByIndex(0);
		clockOutMinuteReset.selectByIndex(0);

		//待ち
		Thread.sleep(5000);

		//指定位置までスクロール
		scrollBy("1000");

		//更新ボタン押下
		final WebElement Reset = webDriver
				.findElement(
						By.cssSelector("#main > div > div > form > div > input"));
		Reset.click();

		//アラート受け入れ
		Alert ResetAlert = webDriver.switchTo().alert();
		ResetAlert.accept();

		//「勤怠情報を直接編集する」リンクを押下
		final WebElement DirectEditing = webDriver.findElement(
				By.cssSelector("#main > div.well.well-lg.p10.mb10 > p > a"));
		DirectEditing.click();

		//画面遷移待ち
		Thread.sleep(5000);

		//勤怠情報直接変更画面に遷移できたかどうか
		final String DirectEditingUrl = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/attendance/update", DirectEditingUrl);

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() throws InterruptedException {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("1000");

		//10/13の出勤が空白で退勤に入力あり
		WebElement workHourSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[4]/select"));
		WebElement workMinuteSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[6]/select"));
		WebElement clockOutHourSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[7]/select"));
		WebElement clockOutMinuteSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[9]/select"));
		Select workHour = new Select(workHourSelect);
		Select workMinute = new Select(workMinuteSelect);
		Select clockOutHour = new Select(clockOutHourSelect);
		Select clockOutMinute = new Select(clockOutMinuteSelect);
		workHour.selectByIndex(0);
		workMinute.selectByIndex(0);
		clockOutHour.selectByIndex(19);
		clockOutMinute.selectByIndex(1);

		getEvidence(new Object() {
		}, "1");

		//更新ボタン押下
		final WebElement update = webDriver
				.findElement(
						By.cssSelector("#main > div > div > form > div > input"));
		update.click();

		//アラート受け入れ
		Alert Alert = webDriver.switchTo().alert();
		Alert.accept();

		//インナー要素のスクロールを指定位置へ
		final WebElement scroll = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody"));
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + 300;", scroll);

		//エラーが出ているかどうか
		final String clockOutError = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/ul/li[1]/span"))
				.getText();
		assertEquals("* 出勤情報がないため退勤情報を入力出来ません。", clockOutError);

		getEvidence(new Object() {
		}, "2");

		//リセット用スクロール
		WebElement workHourSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[4]/select"));
		WebElement workMinuteSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[6]/select"));
		WebElement clockOutHourSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[7]/select"));
		WebElement clockOutMinuteSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[9]/select"));
		Select workHourReset = new Select(workHourSelectReset);
		Select workMinuteReset = new Select(workMinuteSelectReset);
		Select clockOutHourReset = new Select(clockOutHourSelectReset);
		Select clockOutMinuteReset = new Select(clockOutMinuteSelectReset);
		workHourReset.selectByIndex(0);
		workMinuteReset.selectByIndex(0);
		clockOutHourReset.selectByIndex(0);
		clockOutMinuteReset.selectByIndex(0);

		//待ち
		Thread.sleep(5000);

		//指定位置までスクロール
		scrollBy("1000");

		//更新ボタン押下
		final WebElement Reset = webDriver
				.findElement(
						By.cssSelector("#main > div > div > form > div > input"));
		Reset.click();

		//アラート受け入れ
		Alert ResetAlert = webDriver.switchTo().alert();
		ResetAlert.accept();

		//「勤怠情報を直接編集する」リンクを押下
		final WebElement DirectEditing = webDriver.findElement(
				By.cssSelector("#main > div.well.well-lg.p10.mb10 > p > a"));
		DirectEditing.click();

		//画面遷移待ち
		Thread.sleep(5000);

		//勤怠情報直接変更画面に遷移できたかどうか
		final String DirectEditingUrl = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/attendance/update", DirectEditingUrl);

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() throws InterruptedException {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("1000");

		//10/13の出勤が退勤よりも遅い時間
		WebElement workHourSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[4]/select"));
		WebElement workMinuteSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[6]/select"));
		WebElement clockOutHourSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[7]/select"));
		WebElement clockOutMinuteSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[9]/select"));
		Select workHour = new Select(workHourSelect);
		Select workMinute = new Select(workMinuteSelect);
		Select clockOutHour = new Select(clockOutHourSelect);
		Select clockOutMinute = new Select(clockOutMinuteSelect);
		workHour.selectByIndex(11);
		workMinute.selectByIndex(1);
		clockOutHour.selectByIndex(9);
		clockOutMinute.selectByIndex(1);

		getEvidence(new Object() {
		}, "1");

		//更新ボタン押下
		final WebElement update = webDriver
				.findElement(
						By.cssSelector("#main > div > div > form > div > input"));
		update.click();

		//アラート受け入れ
		Alert Alert = webDriver.switchTo().alert();
		Alert.accept();

		//インナー要素のスクロールを指定位置へ
		final WebElement scroll = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody"));
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + 300;", scroll);

		//エラーが出ているかどうか
		final String Error = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/ul/li[1]/span"))
				.getText();
		assertEquals("* 退勤時刻[7]は出勤時刻[7]より後でなければいけません。", Error);

		getEvidence(new Object() {
		}, "2");

		//リセット用スクロール
		WebElement workHourSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[4]/select"));
		WebElement workMinuteSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[6]/select"));
		WebElement clockOutHourSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[7]/select"));
		WebElement clockOutMinuteSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[9]/select"));
		Select workHourReset = new Select(workHourSelectReset);
		Select workMinuteReset = new Select(workMinuteSelectReset);
		Select clockOutHourReset = new Select(clockOutHourSelectReset);
		Select clockOutMinuteReset = new Select(clockOutMinuteSelectReset);
		workHourReset.selectByIndex(0);
		workMinuteReset.selectByIndex(0);
		clockOutHourReset.selectByIndex(0);
		clockOutMinuteReset.selectByIndex(0);

		//待ち
		Thread.sleep(5000);

		//指定位置までスクロール
		scrollBy("1000");

		//更新ボタン押下
		final WebElement Reset = webDriver
				.findElement(
						By.cssSelector("#main > div > div > form > div > input"));
		Reset.click();

		//アラート受け入れ
		Alert ResetAlert = webDriver.switchTo().alert();
		ResetAlert.accept();

		//「勤怠情報を直接編集する」リンクを押下
		final WebElement DirectEditing = webDriver.findElement(
				By.cssSelector("#main > div.well.well-lg.p10.mb10 > p > a"));
		DirectEditing.click();

		//画面遷移待ち
		Thread.sleep(5000);

		//勤怠情報直接変更画面に遷移できたかどうか
		final String DirectEditingUrl = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/attendance/update", DirectEditingUrl);

	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() throws InterruptedException {
		// TODO ここに追加
		//指定位置までスクロール
		scrollBy("1000");

		//10/13の出退勤時間を超える中抜け時間
		WebElement workHourSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[4]/select"));
		WebElement workMinuteSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[6]/select"));
		WebElement clockOutHourSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[7]/select"));
		WebElement clockOutMinuteSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[9]/select"));
		WebElement HollowSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[10]/select"));
		Select workHour = new Select(workHourSelect);
		Select workMinute = new Select(workMinuteSelect);
		Select clockOutHour = new Select(clockOutHourSelect);
		Select clockOutMinute = new Select(clockOutMinuteSelect);
		Select Hollow = new Select(HollowSelect);
		workHour.selectByIndex(10);
		workMinute.selectByIndex(1);
		clockOutHour.selectByIndex(11);
		clockOutMinute.selectByIndex(1);
		Hollow.selectByIndex(8);

		getEvidence(new Object() {
		}, "1");

		//更新ボタン押下
		final WebElement update = webDriver
				.findElement(
						By.cssSelector("#main > div > div > form > div > input"));
		update.click();

		//アラート受け入れ
		Alert Alert = webDriver.switchTo().alert();
		Alert.accept();

		//インナー要素のスクロールを指定位置へ
		final WebElement scroll = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody"));
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + 300;", scroll);

		//エラーが出ているかどうか
		final String Error = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/ul/li/span"))
				.getText();
		assertEquals("* 中抜け時間が勤務時間を超えています。", Error);

		getEvidence(new Object() {
		}, "2");

		//リセット用スクロール
		WebElement workHourSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[4]/select"));
		WebElement workMinuteSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[6]/select"));
		WebElement clockOutHourSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[7]/select"));
		WebElement clockOutMinuteSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[9]/select"));
		WebElement HollowSelectReset = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[10]/select"));

		Select workHourReset = new Select(workHourSelectReset);
		Select workMinuteReset = new Select(workMinuteSelectReset);
		Select clockOutHourReset = new Select(clockOutHourSelectReset);
		Select clockOutMinuteReset = new Select(clockOutMinuteSelectReset);
		Select HollowReset = new Select(HollowSelectReset);
		workHourReset.selectByIndex(0);
		workMinuteReset.selectByIndex(0);
		clockOutHourReset.selectByIndex(0);
		clockOutMinuteReset.selectByIndex(0);
		HollowReset.selectByIndex(0);

		//待ち
		Thread.sleep(5000);

		//指定位置までスクロール
		scrollBy("1000");

		//更新ボタン押下
		final WebElement Reset = webDriver
				.findElement(
						By.cssSelector("#main > div > div > form > div > input"));
		Reset.click();

		//アラート受け入れ
		Alert ResetAlert = webDriver.switchTo().alert();
		ResetAlert.accept();

		//「勤怠情報を直接編集する」リンクを押下
		final WebElement DirectEditing = webDriver.findElement(
				By.cssSelector("#main > div.well.well-lg.p10.mb10 > p > a"));
		DirectEditing.click();

		//画面遷移待ち
		Thread.sleep(5000);

		//勤怠情報直接変更画面に遷移できたかどうか
		final String DirectEditingUrl = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/attendance/update", DirectEditingUrl);

	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {
		// TODO ここに追加
		//100文字以上のカウント
		String baseText = "100文字以上ループ";
		int repeatCount = 11;
		String repeatedText = baseText.repeat(repeatCount);

		//指定位置までスクロール
		scrollBy("1000");

		//10/13の備考が100文字超
		WebElement workHourSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[4]/select"));
		WebElement workMinuteSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[6]/select"));
		WebElement clockOutHourSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[7]/select"));
		WebElement clockOutMinuteSelect = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[9]/select"));
		WebElement note = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[8]/td[12]/input"));

		Select workHour = new Select(workHourSelect);
		Select workMinute = new Select(workMinuteSelect);
		Select clockOutHour = new Select(clockOutHourSelect);
		Select clockOutMinute = new Select(clockOutMinuteSelect);
		workHour.selectByIndex(10);
		workMinute.selectByIndex(1);
		clockOutHour.selectByIndex(19);
		clockOutMinute.selectByIndex(1);
		note.clear();
		note.sendKeys(repeatedText);

		getEvidence(new Object() {
		}, "1");

		//更新ボタン押下
		final WebElement update = webDriver
				.findElement(
						By.cssSelector("#main > div > div > form > div > input"));
		update.click();

		//アラート受け入れ
		Alert Alert = webDriver.switchTo().alert();
		Alert.accept();

		//インナー要素のスクロールを指定位置へ
		final WebElement scroll = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/form/table/tbody"));
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + 300;", scroll);

		//エラーが出ているかどうか
		final String Error = webDriver
				.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div/ul/li/span	"))
				.getText();
		assertEquals("* 備考の長さが最大値(100)を超えています。", Error);

		getEvidence(new Object() {
		}, "2");
	}

}
