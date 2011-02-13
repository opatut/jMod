package JMod;

public enum EventType {
	KeyPressed, //Done OnKeyPressed(int key)
	KeyReleased,
	MouseButtonDown,
	MouseButtonUp,
	BeforeLogin,
	AfterLogin, //Done OnLogin(int protocolVersion)
	BeforeLogout,
	AfterLogout,
	ServerHandshake, //Done OnServerHandshake(String ServerID)
	BeginType,
	BeforeSendMessage,
	AfterSendMessage,
	MessageReceived, //Done OnMessageReceived(String message)
	Kicked, //Done OnKicked()
	Banned,
	Spawning,
	BlockPlaced, //Done OnBlockPlaced(int itemID,int posX,int posY,int posZ)
	BlockRemoved,
	BlockClicked,
	BlockRightClicked,
	ArmSwing,
	UseItem,
	DropItem,
	CollectItem, //Done OnPlayerCollectItem(Entity Item,Entity Player)
	ChangeItemSelection,
	OpenInventory,
	CloseInventory,
	PauseGame,
	HealthChanged,
	ResumeGame,
	UpdateGame, //Done OnGameUpdate()
	DaylightChanged,
	DrawWorld,
	DrawGUI,
	Crafting
}
