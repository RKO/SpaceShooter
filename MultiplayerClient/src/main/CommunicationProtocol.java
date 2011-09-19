package main;

public class CommunicationProtocol {
	public static final String MESSSAGE_SEPERATOR = "MSG:";
	public static final String SERVER_WELCOME_MESSAGE = "Server accepting connection...";
	public static final String CLOSE_MESSAGE = "s:close";
	public static final String NAME_MESSAGE = "new:";
	public static final String APPROVED_MESSAGE = "conok";
	public static final String CHAT_MESSAGE = "cMsg:";
	public static final String MOVE_REQUEST = "move:";
	public static final String MOVE_ACCEPT = "moveOk:";
	public static final String POSITION_MESSAGE = "pos:";
	public static final String PROJECTILE_MESSAGE = "proj:";
	public static final String PLAYER_LEFT = "pLeft:";
	public static final String UPGRADE_EVENT = "upg:";
	
	public static final int UPGRADE_LASER = 0;
	public static final int UPGRADE_MISSILE = 1;
	public static final int UPGRADE_HULL = 2;
	public static final int UPGRADE_SHIELD = 3;
	public static final int UPGRADE_SPEED = 4;
}
