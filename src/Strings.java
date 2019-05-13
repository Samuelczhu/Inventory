/**
 * This class holds the strings used in this program
 * It can use to provide translation easily
 * This program is designed in two language for now: Chinese and English
 *
 * @author samuel zhu
 * @version 1.0
 */
public class Strings {

    public static final int CHINESE = 0;
    public static final int ENGLISH = 1;

    //hold the language to return
    private static int language = CHINESE; //0 for Chinese and 1 for english, default Chinese

    /**
     * This class is not meant to instantiate
     */
    private Strings() {}

    /**
     * Set the language, only Chinese and English are valid so far
     * @param language the language to be set
     */
    public static void setLanguage(int language) {
        if (language!=ENGLISH && language!= CHINESE)
            throw new IllegalArgumentException("Invalid language");
        Strings.language = language;
    }

    /**
     * Get the selected language
     * @return String to represent the language
     */
    public static String getLanguage() {
        if (language==CHINESE)
            return "Chinese";
        return "English";
    }

    /**
     * Get the title, array index: 0
     * @return String for the title
     */
    public static String appTitle() {
        if (language == CHINESE)
            return "储存列表";
        return "Inventory";
    }

    /**
     * Get the welcome status
     * @return String for welcome status
     */
    public static String welcomeStatus() {
        if (language == CHINESE)
            return "欢迎使用储存列表，按＋添加物品。";
        return "Welcome to Inventory, press + to add item";
    }

    /**
     * @return String for file
     */
    public static String file() {
        if (language == CHINESE)
            return "文件";
        return "File";
    }

    /**
     * @return String for quit
     */
    public static String quit() {
        if (language==CHINESE)
            return "退出";
        return "Quit";
    }

    /**
     * @return String for main title
     */
    public static String mainTitle() {
        if (language==CHINESE) {
            return "物品列表";
        }
        return "Item Table";
    }

    /**
     * @return names for the columns
     */
    public static String[] columnNames() {
        if (language==CHINESE)
            return new String[] {"序号","名称","网址","价格","数量"};
        return new String[]{"Number","Name","Website","Price","Quantity"};
    }

    /**
     * @return String for language
     */
    public static String language() {
        if (language==CHINESE)
            return "语言";
        return "Language";
    }

    /**
     * @return String for Chinese
     */
    public static String chinese(){
        if (language==CHINESE)
            return "中文";
        return "Chinese";
    }

    /**
     * @return String for English
     */
    public static String english() {
        if (language==CHINESE)
            return "英文";
        return "English";
    }

    /**
     * @return String for edit
     */
    public static String edit() {
        if (language==CHINESE)
            return "编辑";
        return "Edit";
    }

    /**
     * @return String for type
     */
    public static String types() {
        if (language==CHINESE)
            return "种类";
        return "Types";
    }

    /**
     * @return String for total quantity
     */
    public static String totalQuantity() {
        if (language==CHINESE)
            return "总数量";
        return "Total Quantity";
    }

    /**
     * @return String for total price
     */
    public static String totalPrice() {
        if (language==CHINESE)
            return "总价格";
        return "Total Price";
    }

    /**
     * @return String for add item
     */
    public static String addItem() {
        if (language==CHINESE)
            return "添加物品";
        return "Add Item";
    }

    /**
     * @return String for delete item
     */
    public static String deleteItem() {
        if (language==CHINESE)
            return "删除物品";
        return "Delete Item";
    }

    /**
     * @return String for edit item
     */
    public static String editItem() {
        if (language==CHINESE)
            return "编辑物品";
        return "Edit Item";
    }

    /**
     * @return String for edit url
     */
    public static String copyUrl() {
        if (language==CHINESE)
            return "复制链接";
        return "Copy Url";
    }

    /**
     * @return String for copy url error
     */
    public static String copyUrlError() {
        if (language==CHINESE)
            return "请选择单个物品来复制链接";
        return "Please select single item to copy url";
    }

    /**
     * @return String for edit item error
     */
    public static String editItemError() {
        if (language==CHINESE)
            return "请选择单个物品来编辑";
        return "Please select single item to edit";
    }

    /**
     * @return String for delete nothing
     */
    public static String deleteEmpty() {
        if (language==CHINESE)
            return "请选择要删除的物品";
        return "Please select item(s) to delete";
    }

    /**
     * @return String for undo delete action
     */
    public static String undoDelete() {
        if (language==CHINESE)
            return "撤销删除";
        return "Undo Delete";
    }

    /**
     * @return String to show that nothing is deleted yet
     */
    public static String nothingDeleted() {
        if (language==CHINESE)
            return "暂时没有删除任何内容";
        return "Nothing is deleted now";
    }

    /**
     * @return String for move item up
     */
    public static String up() {
        if (language==CHINESE)
            return "升";
        return "Up";
    }

    /**
     * @return String for move item down
     */
    public static String down() {
        if (language==CHINESE)
            return "降";
        return "Down";
    }

    /**
     * @return String to tell user that the selected item is already on top
     */
    public static String onTop() {
        if (language==CHINESE)
            return "此物品已置顶";
        return "This item already on the top";
    }

    /**
     * @return String to tell user that only one item is allow to move up or down at a time
     */
    public static String onlyOneAllowMove() {
        if (language==CHINESE)
            return "只限单个物品移动，请选择单个物品";
        return "Only allows one item to move. Please select single item";
    }

    /**
     * @return inform user that the selected item is already at the bottom
     */
    public static String onBottom() {
        if (language==CHINESE)
            return "此物品已置底";
        return "This item already at the bottom";
    }

    /**
     * @return the String for name input
     */
    public static String name() {
        if (language==CHINESE)
            return "物品名称：";
        return "Item name: ";
    }

    /**
     * @return the String for url input
     */
    public static String url() {
        if (language==CHINESE)
            return "物品网址：";
        return "Item url: ";
    }

    /**
     * @return the String for price input
     */
    public static String price(){
        if (language==CHINESE)
            return "物品价格：";
        return "Item price: ";
    }

    /**
     * @return the String for quantity input
     */
    public static String quantity(){
        if (language==CHINESE)
            return "物品数量：";
        return "Number of Item: ";
    }

    /**
     * @return String for cancel
     */
    public static String cancel() {
        if (language==CHINESE)
            return "取消";
        return "Cancel";
    }

    /**
     * @return String for OK
     */
    public static String OK() {
        if (language==CHINESE)
            return "确定";
        return "OK";
    }

    /**
     * @return String to inform user to enter valid inputs
     */
    public static String invalidInput() {
        if (language==CHINESE)
            return "请输入有效的信息";
        return "Please enter valid inputs";
    }

    /**
     * @return String to inform user to handle one item at a time
     */
    public static String handleOne() {
        if (language==CHINESE)
            return "请一次处理一件物品";
        return "Please handle one item at a time";
    }

    /**
     * @return String for setting
     */
    public static String setting() {
        if (language==CHINESE)
            return "设置";
        return "Setting";
    }

    /**
     * @return String for open
     */
    public static String open() {
        if (language==CHINESE)
            return "打开";
        return "Open";
    }

    /**
     * @return String for save
     */
    public static String save() {
        if (language==CHINESE)
            return "保存";
        return "Save";
    }

    /**
     * @return String for save as
     */
    public static String saveAs() {
        if (language==CHINESE)
            return "另存为";
        return "Save As";
    }

    /**
     * @return String for export text
     */
    public static String export(){
        if (language==CHINESE)
            return "输出文档";
        return "Export Text";
    }

    /**
     * @return String for delete file
     */
    public static String deleteFile() {
        if (language==CHINESE)
            return "删除文件";
        return "Delete File";
    }

    /**
     * @return String to inform user failed to load data
     */
    public static String failLoadData() {
        if (language==CHINESE)
            return "无法读取信息";
        return "Failed to load data";
    }

    /**
     * @return String to inform user failed to store data
     */
    public static String failStoreData() {
        if (language==CHINESE)
            return "保存失败";
        return "Failed to store data";
    }

    /**
     * @return String to inform user saved successfully
     */
    public static String saved() {
        if (language==CHINESE)
            return "保存成功";
        return "Saved successfully";
    }

    /**
     * @return String for rename
     */
    public static String rename() {
        if (language==CHINESE)
            return "重命名";
        return "Rename";
    }

    /**
     * @return String to inform user rename successfully
     */
    public static String renameSuccess() {
        if (language==CHINESE)
            return "重命名成功";
        return "Rename successfully";
    }

    /**
     * @return String to inform user fail to rename
     */
    public static String renameFail() {
        if (language==CHINESE)
            return "重命名失败";
        return "Fail to rename";
    }

    /**
     * @return String to cancel rename
     */
    public static String renameCancel() {
        if (language==CHINESE)
            return "取消重命名";
        return "Cancel renaming";
    }

    /**
     * @return String to ask user saved before exit?
     */
    public static String saveBeforeExit() {
        if (language==CHINESE)
            return "保存文件吗？";
        return "Save before exit?";
    }

    /**
     * @return String for please confirm
     */
    public static String pleaseConfirm() {
        if (language==CHINESE)
            return "请选择";
        return "Please confirm";
    }

    /**
     * @return fail to save setting
     */
    public static String failSaveSetting() {
        if (language==CHINESE)
            return "保存设置失败";
        return "Fail to save setting";
    }

    /**
     * @return String to tell user delete successfully
     */
    public static String deleteSuccess() {
        if (language==CHINESE)
            return "删除文件成功";
        return "Delete file successfully";
    }

    /**
     * @return String to inform user fail to delete file
     */
    public static String deleteFail() {
        if (language==CHINESE)
            return "删除文件失败，请手动删除";
        return "Fail to delete file. Please delete manually";
    }

    /**
     * @return String to inform user no file
     */
    public static String noFile() {
        if (language==CHINESE)
            return "无文件";
        return "No file";
    }

    /**
     * @return String for show in explorer
     */
    public static String showInExplorer() {
        if (language==CHINESE)
            return "在资源管理器里打开";
        return "Show in Explorer";
    }

    /**
     * @return String to inform user unable to open the file explorer
     */
    public static String unableToOpenInExplorer() {
        if (language==CHINESE)
            return "无法在资源管理器里打开";
        return "Unable to open the file explorer";
    }

    /**
     * @return String for creating new file
     */
    public static String newFile() {
        if (language==CHINESE)
            return "新建";
        return "New";
    }

    /**
     * @return String to inform user not saved
     */
    public static String notSaved() {
        if (language==CHINESE)
            return "未保存";
        return "Not saved";
    }

    /**
     * @return String to inform user new file created
     */
    public static String fileCreated() {
        if (language==CHINESE)
            return "新建文件成功";
        return "New file created";
    }

    /**
     * @return String to inform user fail to create new file
     */
    public static String fileNotCreated() {
        if (language==CHINESE)
            return "新建文件失败";
        return "Fail to create new file";
    }

    /**
     * Output the export format for the *.txt file
     * @return String in the format of: #. name website price quantity
     */
    public static String exportFormat() {
        if (language==CHINESE)
            return "#. 名称\t    网址\t    单价\t数量";
        return "#. Name\t    Website\t   Price\tQuantity";
    }

    /**
     * @return String to inform user export successfully
     */
    public static String exportSuccess() {
        if (language==CHINESE)
            return "文档输出成功";
        return "Text exported successfully";
    }

    /**
     * @return String to inform user export failed
     */
    public static String exportFail() {
        if (language==CHINESE)
            return "文档输出失败";
        return "Failed to export text";
    }

    /**
     * @return String for status of added new item
     */
    public static String itemAdded() {
        if (language==CHINESE)
            return "新物品添加成功";
        return "New item added successfully";
    }

    /**
     * @return String for status of deleted selected item
     */
    public static String itemDeleted() {
        if (language==CHINESE)
            return "物品删除成功";
        return "Item deleted successfully";
    }

    /**
     * @return String for status of edited selected item
     */
    public static String itemEdited() {
        if (language==CHINESE)
            return "物品编辑成功";
        return "Item edited successfully";
    }

    /**
     * @return String for status of url copied
     */
    public static String urlCopied() {
        if (language==CHINESE)
            return "网址复制成功";
        return "Url copied successfully";
    }

    /**
     * @return String for status of restored deleted item successfully
     */
    public static String undoDeleted() {
        if (language==CHINESE)
            return "撤销删除成功";
        return "Restored deleted item successfully";
    }

    /**
     * @return String for status of item moved up successfully
     */
    public static String itemMovedUp() {
        if (language==CHINESE)
            return "物品上移成功";
        return "Item moved up successfully";
    }

    /**
     * @return String for status of item moved down successfully
     */
    public static String itemMovedDown(){
        if (language==CHINESE)
            return "物品下移成功";
        return "Item moved down successfully";
    }

    /**
     * @return String for status of opened new file
     */
    public static String openedNewFile() {
        if (language==CHINESE)
            return "打开文件成功";
        return "Open file successfully";
    }

    /**
     * @return String for status of opened in explorer successfully
     */
    public static String openedInExplorer() {
        if (language==CHINESE)
            return "已在资源管理器里打开";
        return "Opened in file explorer";
    }
}
