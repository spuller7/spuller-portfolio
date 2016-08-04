using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class Shop : MonoBehaviour
{
    public GameObject playerObject;
    public int currentCannonBall;
    public bool gunsActive = true;

    public GameObject ShopMenu;
    public GameObject Exit_b;
    public GameObject Ships_m;
    public GameObject Cannons_m;
    public GameObject Munitions_m;
    public GameObject Crew_m;
    public string shiptype;
    public bool MenuToggle;
    public GameObject ShipText;
    public GameObject CannonText;
    public GameObject MunitionsText;
    public GameObject CrewText;
    public Text ShopTextMoney;
    public int ShopMoney; // PLAYER'S MONEY
    public int shipValue; //1=Cutter 2=Brig 3=Double
    public int cannonValue;

    //Ship Menu
        //Text
        public Text CutterPriceText;
        public Text CutterPriceRedText;
        public Text BrigPriceText;
        public Text BrigPriceRedText;
        public Text DoublePriceText;
        public Text DoublePriceRedText;
        //Price Amount
        public int StartingMoney = 975;
        public int CutterPrice = 200;
        public int BrigPrice = 600;
        public int DoublePrice = 1000;
        //Inventory Amount
        public bool OwnedCutter;
        public bool OwnedBrig;
        public bool OwnedDouble;

    //Cannon Menu 
        //Text
        public Text CulverinPriceText;
        public Text LongNinePriceText;
        public Text DemiCulverinPriceText;
        public Text CarronadePriceText;
        public Text PaixhansPriceText;
        public Text CulverinPriceRedText;
        public Text LongNinePriceRedText;
        public Text DemiCulverinPriceRedText;
        public Text CarronadePriceRedText;
        public Text PaixhansPriceRedText;
        //Price Amount
        public int longNinePrice = 50;
        public int CulverinPrice = 35;
        public int CarronadePrice = 60;
        public int PaixhansPrice = 80;
        public int DemiCulverinPrice = 60;

    //Munitions Menu
        //Text
        public Text OwnedRoundShotText;
        public Text OwnedCannisterShotText;
        public Text OwnedDoubleShotText;
        public Text OwnedLangrageShotText;
        public Text OwnedExplosiceShellShotText;
        public Text OwnedChainShotText;
        public Text RoundShotTextPrice;
        public Text CannisterShotPriceText;
        public Text DoubleShotPriceText;
        public Text LangrageShotPriceText;
        public Text ExplosiveShellPriceText;
        public Text ChainShotPriceText;
        public Text RoundShotPriceRedText;
        public Text CannisterShotPriceRedText;
        public Text DoubleShotPriceRedText;
        public Text LangrageShotPriceRedText;
        public Text ExplosiveShellPriceRedText;
        public Text ChainShotPriceRedText;
        //Hud Info
        public Text HudOwnedRoundShotText;
        public Text HudOwnedCannisterShotText;
        public Text HudOwnedDoubleShotText;
        public Text HudOwnedLangrageShotText;
        public Text HudOwnedExplosiveShotText;
        public Text HudOwnedChainShotText;
        public Text HudSRound;
        public Text HudSChain;
        public Text HudSDouble;
        public Text HudSCannister;
        public Text HudSExplosion;
        public Text HudSLangrage;
        //Price
        public int RoundShotPrice = 5;
        public int CannisterShot = 5;
        public int ChainShot = 8;
        public int LangrageShot = 1;
        public int ExplodingShellPrice = 10;
        public int DoubleShotPrice = 6;
        //Owned Shots
        public int OwnedRoundShots;
        public int OwnedCannisterShots;
        public int OwnedChainShots;
        public int OwnedLangrageShots;
        public int OwnedExplodingShots;
        public int OwnedDoubleShots;
        bool zero = false;

    //Crew Shop
        //Text
        public Text ownedCrewText;
        public Text crewRedText;
        public Text ownedRepairmenText;
        public Text repairmenRedText;
        public Text ownedLooterText;
        public Text looterRedText;
        //Crew$$$$$$$$$$$$$$$$$$$$$$
        public int StartingCrew = 20;
        public int CrewPrice = 10;
        public int RepairmenPrice = 15;
        public int LootersPrice = 15;
        public int OwnedCrew;
        public int OwnedRepairmen;
        public int OwnedLooters;
        //Prices^^


    //HUUD AND TEXT
    public Text HudTextMoney;
    public Scrollbar healthBar;
    public Scrollbar reloadBar;

    //BUTTONS TO BUY ITEMS
    public GameObject CutterBuy;
    public GameObject BrigBuy;
    public GameObject DoubleBuy;
    public GameObject CulverinBuy;
    public GameObject LongNineBuy;
    public GameObject DemiCulverinBuy;
    public GameObject CarronadedBuy;
    public GameObject PaixhansBuy;
    public GameObject RoundShotBuy;
    public GameObject CannisterShotBuy;
    public GameObject ChainShotBuy;
    public GameObject LangragShotBuy;
    public GameObject ExplodingShotBuy;
    public GameObject DoubleShotBuy;
    public GameObject CrewBuy;
    public GameObject RepairmenBuy;
    public GameObject LootersBuy;


    private bool showCuror;

    //Current Game Statuses
    bool middleControl = false;
    public int earnValue = 2;

    public Text hudOwnedCrew;
    public Text hudOwnedRepairmen;
    public Text hudOwnedLooters;

    //888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
    void Start()
    {
        //once game actually starts note
        InvokeRepeating("moneyCounter", 1, 2);

        showCuror = true;
        shipValue = 1;
        MenuToggle = false;
        ShopMoney = StartingMoney;
        OwnedCrew = StartingCrew;
        OwnedRepairmen = 0;
        OwnedLooters = 0;
        shiptype = "Cutter";

        CutterPriceText.text = CutterPrice.ToString();
        CutterPriceRedText.text = CutterPrice.ToString();
        BrigPriceText.text = BrigPrice.ToString();
        BrigPriceRedText.text = BrigPrice.ToString();
        DoublePriceText.text = DoublePrice.ToString();
        DoublePriceRedText.text = DoublePrice.ToString();
        CulverinPriceText.text = CulverinPrice.ToString();
        CulverinPriceRedText.text = CulverinPrice.ToString();
        LongNinePriceText.text = longNinePrice.ToString();
        LongNinePriceRedText.text = longNinePrice.ToString();
        DemiCulverinPriceText.text = DemiCulverinPrice.ToString();
        DemiCulverinPriceRedText.text = DemiCulverinPrice.ToString();
        CarronadePriceText.text = CarronadePrice.ToString();
        CarronadePriceRedText.text = CarronadePrice.ToString();
        PaixhansPriceText.text = PaixhansPrice.ToString();
        PaixhansPriceRedText.text = PaixhansPrice.ToString();
        RoundShotPriceRedText.text = RoundShotPrice.ToString();
        RoundShotTextPrice.text = RoundShotPrice.ToString();
        CannisterShotPriceText.text = CannisterShot.ToString();
        CannisterShotPriceRedText.text = CannisterShot.ToString();
        DoubleShotPriceText.text = DoubleShotPrice.ToString();
        DoubleShotPriceRedText.text = DoubleShotPrice.ToString();
        ChainShotPriceRedText.text = ChainShot.ToString();
        ChainShotPriceText.text = ChainShot.ToString();
        ExplosiveShellPriceText.text = ExplodingShellPrice.ToString();
        ExplosiveShellPriceRedText.text = ExplodingShellPrice.ToString();
        LangrageShotPriceRedText.text = LangrageShot.ToString();
        LangrageShotPriceText.text = LangrageShot.ToString();

        hudOwnedCrew.text = OwnedCrew.ToString();
        hudOwnedRepairmen.text = OwnedRepairmen.ToString();
        hudOwnedLooters.text = OwnedLooters.ToString();

        OwnedRoundShots = 20;
        OwnedCannisterShots = 0;
        OwnedChainShots = 0;
        OwnedLangrageShots = 0;
        OwnedDoubleShots = 0;
        OwnedExplodingShots = 0;
        HudSCannister.gameObject.SetActive(false);
        HudSChain.gameObject.SetActive(false);
        HudSDouble.gameObject.SetActive(false);
        HudSExplosion.gameObject.SetActive(false);
        HudSLangrage.gameObject.SetActive(false);
        HudSRound.gameObject.SetActive(true);

        zero = false;
        currentCannonBall = 1;
        ShopMenu.SetActive(false);
        Exit_b.SetActive(true);
        Ships_m.SetActive(true);
        Cannons_m.SetActive(false);
        Munitions_m.SetActive(false);
        Crew_m.SetActive(false);

    }

    void Update()
    {
        changeCannonBall();
        changeCannonBallAmount();
        Cursor.visible = showCuror;
        Screen.lockCursor = !showCuror;
        ShopTextMoney.text = ShopMoney.ToString();
        HudTextMoney.text = ShopMoney.ToString();
        
        if (Input.GetKeyDown(KeyCode.B))
        {
            ShopMenuToggle();
        }
    }
    public void setPlayerObject(GameObject playerObj)
    {
        playerObject = playerObj;
        playerObject.GetComponent<Health>().setShop(Camera.main.GetComponent<Shop>());

    }
    public void ShopMenuToggle()
    {
        if (MenuToggle == false)
        {
            MenuToggle = true;
            showCuror = true;
            ShopMenu.SetActive(true);
            Exit_b.SetActive(true);
            Ships_m.SetActive(true);
            ShipMenuToggle();
            Cannons_m.SetActive(false);
            Munitions_m.SetActive(false);
            Crew_m.SetActive(false);
            playerObject.GetComponent<CarScript>().activateCon(false, 0);
            Debug.Log("Opened Shop Menu");
        }
        else if (MenuToggle == true)
        {
            MenuToggle = false;
            showCuror = false;
            ShopMenu.SetActive(false);
            Exit_b.SetActive(true);
            Ships_m.SetActive(true);
            Cannons_m.SetActive(false);
            Munitions_m.SetActive(false);
            Crew_m.SetActive(false);
            Debug.Log("Closed Shop Menu");
            playerObject.GetComponent<CarScript>().activateCon(true, 0);

        }
    }
    //SHIP MENU ===========================================================================================================
    public void ShipMenuToggle()
    {
        //Highlight Text
        ShipText.SetActive(true);
        CannonText.SetActive(false);
        MunitionsText.SetActive(false);
        CrewText.SetActive(false);
        //MenuSelect
        Ships_m.SetActive(true);
        Cannons_m.SetActive(false);
        Munitions_m.SetActive(false);
        Crew_m.SetActive(false);
        bool cutterActive = false;
        bool brigActive = false;
        bool doubleActive = false;

        if (ShopMoney > CutterPrice)
        {
            cutterActive = true;
        }
        if (ShopMoney > BrigPrice)
        {
            brigActive = true;
        }
        if (ShopMoney > DoublePrice)
        {
            doubleActive = true;
        }
        if (shiptype == "Cutter")
        {
            cutterActive = false;
        }
        if (shiptype == "Brig")
        {
            brigActive = false;
        }
        if (shiptype == "Double")
        {
            doubleActive = false;
        }
        CutterBuy.SetActive(cutterActive);

        BrigBuy.SetActive(brigActive);

        DoubleBuy.SetActive(doubleActive);

        if (ShopMoney >= CutterPrice)
        {
            CutterPriceRedText.gameObject.SetActive(false);
        }
        else
        {
            CutterPriceRedText.gameObject.SetActive(true);
        }
        if (ShopMoney >= BrigPrice)
        {
            BrigPriceRedText.gameObject.SetActive(false);
        }
        else
        {
            BrigPriceRedText.gameObject.SetActive(true);
        }
        if (ShopMoney >= DoublePrice)
        {
            DoublePriceRedText.gameObject.SetActive(false);
        }
        else
        {
            DoublePriceRedText.gameObject.SetActive(true);
        }
    }
    //CANNON MENU==========================================================================================================
    public void CannonMenuToggle()
    {
        ShipText.SetActive(false);
        CannonText.SetActive(true);
        MunitionsText.SetActive(false);
        CrewText.SetActive(false);
        Ships_m.SetActive(false);
        Cannons_m.SetActive(true);
        Munitions_m.SetActive(false);
        Crew_m.SetActive(false);

        if (ShopMoney >= CulverinPrice)
        {
            CulverinPriceRedText.gameObject.SetActive(false);
            CulverinBuy.SetActive(true);
        }
        else
        {
            CulverinPriceRedText.gameObject.SetActive(true);
            CulverinBuy.SetActive(false);
        }
        if (ShopMoney >= longNinePrice)
        {
            LongNinePriceRedText.gameObject.SetActive(false);
            LongNineBuy.SetActive(true);
        }
        else
        {
            LongNinePriceRedText.gameObject.SetActive(true);
            LongNineBuy.SetActive(false);
        }
        if (ShopMoney >= DemiCulverinPrice)
        {
            DemiCulverinPriceRedText.gameObject.SetActive(false);
            DemiCulverinBuy.SetActive(true);
        }
        else
        {
            DemiCulverinPriceRedText.gameObject.SetActive(true);
            DemiCulverinBuy.SetActive(false);
        }
        if (ShopMoney >= CarronadePrice)
        {
            CarronadePriceRedText.gameObject.SetActive(false);
            CarronadedBuy.SetActive(true);
        }
        else
        {
            CarronadePriceRedText.gameObject.SetActive(true);
            CarronadedBuy.SetActive(false);
        }
        if (ShopMoney >= PaixhansPrice)
        {
            PaixhansPriceRedText.gameObject.SetActive(false);
            PaixhansBuy.SetActive(true);
        }
        else
        {
            PaixhansPriceRedText.gameObject.SetActive(true);
            PaixhansBuy.SetActive(false);
        }
        if (cannonValue == 1)
        {
            CulverinBuy.SetActive(false);
        }
        else if (cannonValue == 2)
        {
            LongNineBuy.SetActive(false);
        }
        else if (cannonValue == 3)
        {
            DemiCulverinBuy.SetActive(false);
        }
        else if (cannonValue == 4)
        {
            CarronadedBuy.SetActive(false);
        }
        else if (cannonValue == 5)
        {
            PaixhansBuy.SetActive(false);
        }
    }
    //MUNITIONS MENU ====================================================================================================
    public void MunitionsMenuToggle()
    {
        ShipText.SetActive(false);
        CannonText.SetActive(false);
        MunitionsText.SetActive(true);
        CrewText.SetActive(false);
        Ships_m.SetActive(false);
        Cannons_m.SetActive(false);
        Munitions_m.SetActive(true);
        Crew_m.SetActive(false);
        if (RoundShotPrice > ShopMoney)
        {
            RoundShotBuy.SetActive(false);
            RoundShotPriceRedText.gameObject.SetActive(true);
        }
        else
        {
            RoundShotBuy.SetActive(true);
            RoundShotPriceRedText.gameObject.SetActive(false);
        }
        if (DoubleShotPrice > ShopMoney)
        {
            DoubleShotBuy.SetActive(false);
            DoubleShotPriceRedText.gameObject.SetActive(true);
        }
        else
        {
            DoubleShotBuy.SetActive(true);
            DoubleShotPriceRedText.gameObject.SetActive(false);
        }
        if (CannisterShot > ShopMoney)
        {
            CannisterShotBuy.SetActive(false);
            CannisterShotPriceRedText.gameObject.SetActive(true);
        }
        else
        {
            CannisterShotBuy.SetActive(true);
            CannisterShotPriceRedText.gameObject.SetActive(false);

        }
        if (ExplodingShellPrice > ShopMoney)
        {
            ExplodingShotBuy.SetActive(false);
            ExplosiveShellPriceRedText.gameObject.SetActive(true);
        }
        else
        {
            ExplodingShotBuy.SetActive(true);
            ExplosiveShellPriceRedText.gameObject.SetActive(false);
        }
        if (LangrageShot > ShopMoney)
        {
            LangragShotBuy.SetActive(false);
            LangrageShotPriceRedText.gameObject.SetActive(true);
        }
        else
        {
            LangragShotBuy.SetActive(true);
            LangrageShotPriceRedText.gameObject.SetActive(false);
        }
        if (ChainShot > ShopMoney)
        {
            ChainShotBuy.SetActive(false);
            ChainShotPriceRedText.gameObject.SetActive(true);
        }
        else
        {
            ChainShotBuy.SetActive(true);
            ChainShotPriceRedText.gameObject.SetActive(false);
        }
    }
    //CREW MENU ==========================================================================================================
    public void CrewMenuToggle()
    {
        ShipText.SetActive(false);
        CannonText.SetActive(false);
        MunitionsText.SetActive(false);
        CrewText.SetActive(true);
        Ships_m.SetActive(false);
        Cannons_m.SetActive(false);
        Munitions_m.SetActive(false);
        Crew_m.SetActive(true);

        if (CrewPrice > ShopMoney && OwnedCrew < 32)
        {
            CrewBuy.SetActive(false);
            crewRedText.gameObject.SetActive(true);
        }
        else
        {
            CrewBuy.SetActive(true);
            crewRedText.gameObject.SetActive(false);
        }
        if (RepairmenPrice > ShopMoney)
        {
            RepairmenBuy.SetActive(false);
            repairmenRedText.gameObject.SetActive(true);

        }
        else
        {
            RepairmenBuy.SetActive(true);
            repairmenRedText.gameObject.SetActive(false);
        }
        if (LootersPrice > ShopMoney)
        {
            LootersBuy.SetActive(false);
            looterRedText.gameObject.SetActive(true);
        }
        else
        {
            looterRedText.gameObject.SetActive(false);
            LootersBuy.SetActive(true);
        }
    }
    //Money gain and refresh
    private void moneyCounter()
    {
        if (middleControl == true)
        {
            earnValue = earnValue + 2;
        }
        if (OwnedLooters > 0)
        {
            earnValue = earnValue + OwnedLooters;
        }
        ShopMoney = ShopMoney + earnValue;
        if (Ships_m.activeSelf == true)
        {
            ShipMenuToggle();
        }
        else if (Cannons_m.activeSelf == true)
        {
            CannonMenuToggle();
        }
        else if (Munitions_m.activeSelf == true)
        {
            MunitionsMenuToggle();
        }
        else if (Crew_m.activeSelf == true)
        {
            CrewMenuToggle();
        }
    }
    //88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
    public void BuyCutter()
    {

        ShopMoney = ShopMoney - CutterPrice;
        //  shiptype = "Cutter";
        shipValue = 1;
        ShopMenuToggle();
        playerObject.GetComponent<CarScript>().activateCon(false, 3);
        playerObject.GetComponent<SpawnDelay>().shipChange(1);
    }
    public void BuyBrig()
    {
        Debug.Log("Bought Brig");
        ShopMoney = ShopMoney - BrigPrice;
        Debug.Log("Current Money: " + ShopMoney);
        // shiptype = "Brig";
        shipValue = 2;
        ShopMenuToggle();
        playerObject.GetComponent<CarScript>().activateCon(false, 2);
        playerObject.GetComponent<SpawnDelay>().shipChange(shipValue);
    }
    public void BuyDouble()
    {
        ShopMoney = ShopMoney - DoublePrice;
        //shiptype = "Double";
        shipValue = 3;
        MenuToggle = false;
        ShopMenuToggle();
        playerObject.GetComponent<CarScript>().activateCon(false, 3);
        playerObject.GetComponent<SpawnDelay>().shipChange(shipValue);
    }
    //88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
    public void BuyCulverin()
    {
        ShopMoney = ShopMoney - CulverinPrice;
        cannonValue = 1;
        boughtCannon();
        CannonMenuToggle();
    }

    public void BuyLongNine()
    {
        ShopMoney = ShopMoney - longNinePrice;
        cannonValue = 2;
        boughtCannon();
        CannonMenuToggle();
    }

    public void BuyDemiCulverin()
    {
        ShopMoney = ShopMoney - DemiCulverinPrice;
        cannonValue = 3;
        boughtCannon();
        CannonMenuToggle();
    }

    public void BuyCarronade()
    {

        ShopMoney = ShopMoney - CarronadePrice;
        cannonValue = 4;
        boughtCannon();
        CannonMenuToggle();
    }

    public void BuyPaixhans()
    {
        ShopMoney = ShopMoney - PaixhansPrice;
        cannonValue = 5;
        boughtCannon();
        CannonMenuToggle();
    }
    public void boughtCannon()
    {
        if (cannonValue == 1)
        {
            playerObject.GetComponent<Ship>().changeCannonDamage(4);
        }
        else if (cannonValue == 2)
        {
            playerObject.GetComponent<Ship>().changeCannonDamage(2);
        }
        else if (cannonValue == 3)
        {
            playerObject.GetComponent<Ship>().changeCannonDamage(5);
        }
        else if (cannonValue == 4)
        {
            playerObject.GetComponent<Ship>().changeCannonDamage(8);
        }
        else if (cannonValue == 5)
        {
            playerObject.GetComponent<Ship>().changeCannonDamage(6);
        }
        playerObject.GetComponent<cannonRangeChange>().changeCannonRange(cannonValue);
    }
    //8888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
    void changeCannonBall()
    {
        if (Input.GetKeyDown(KeyCode.Alpha1))
        {
            HudSCannister.gameObject.SetActive(false);
            HudSChain.gameObject.SetActive(false);
            HudSDouble.gameObject.SetActive(false);
            HudSExplosion.gameObject.SetActive(false);
            HudSLangrage.gameObject.SetActive(false);
            HudSRound.gameObject.SetActive(true);
            changeCannonball(1);
            currentCannonBall = 1;
            zero = false;
        }
        if (Input.GetKeyDown(KeyCode.Alpha2))
        {
            currentCannonBall = 2;

            HudSCannister.gameObject.SetActive(false);
            HudSChain.gameObject.SetActive(true);
            HudSDouble.gameObject.SetActive(false);
            HudSExplosion.gameObject.SetActive(false);
            HudSLangrage.gameObject.SetActive(false);
            HudSRound.gameObject.SetActive(false);
            changeCannonball(2);
            zero = false;
        }
        if (Input.GetKeyDown(KeyCode.Alpha3))
        {
            HudSCannister.gameObject.SetActive(false);
            HudSChain.gameObject.SetActive(false);
            HudSDouble.gameObject.SetActive(true);
            HudSExplosion.gameObject.SetActive(false);
            HudSLangrage.gameObject.SetActive(false);
            HudSRound.gameObject.SetActive(false);
            changeCannonball(3);
            currentCannonBall = 3;
            zero = false;
        }
        if (Input.GetKeyDown(KeyCode.Alpha4))
        {
            HudSCannister.gameObject.SetActive(true);
            HudSChain.gameObject.SetActive(false);
            HudSDouble.gameObject.SetActive(false);
            HudSExplosion.gameObject.SetActive(false);
            HudSLangrage.gameObject.SetActive(false);
            HudSRound.gameObject.SetActive(false);
            changeCannonball(4);
            currentCannonBall = 4;
            zero = false;
        }
        if (Input.GetKeyDown(KeyCode.Alpha5))
        {
            HudSCannister.gameObject.SetActive(false);
            HudSChain.gameObject.SetActive(false);
            HudSDouble.gameObject.SetActive(false);
            HudSExplosion.gameObject.SetActive(true);
            HudSLangrage.gameObject.SetActive(false);
            HudSRound.gameObject.SetActive(false);
            changeCannonball(5);
            currentCannonBall = 5;
            zero = false;
        }
        if (Input.GetKeyDown(KeyCode.Alpha6))
        {
            HudSCannister.gameObject.SetActive(false);
            HudSChain.gameObject.SetActive(false);
            HudSDouble.gameObject.SetActive(false);
            HudSExplosion.gameObject.SetActive(false);
            HudSLangrage.gameObject.SetActive(true);
            HudSRound.gameObject.SetActive(false);
            changeCannonball(6);
            currentCannonBall = 6;
            zero = false;
        }

        if (zero == false)
        {
            if (currentCannonBall == 1 && OwnedRoundShots == 0)
            {
                playerObject.GetComponent<boatControl>().gunActivate(false);
                gunsActive = false;
                zero = true;
            }
            if (currentCannonBall == 2 && OwnedChainShots == 0)
            {
                playerObject.GetComponent<boatControl>().gunActivate(false);
                zero = true;
                gunsActive = false;
            }
            if (currentCannonBall == 3 && OwnedDoubleShots == 0)
            {
                playerObject.GetComponent<boatControl>().gunActivate(false);
                zero = true;
                gunsActive = false;
            }
            if (currentCannonBall == 4 && OwnedCannisterShots == 0)
            {
                playerObject.GetComponent<boatControl>().gunActivate(false);
                zero = true;
                gunsActive = false;
            }
            if (currentCannonBall == 5 && OwnedExplodingShots == 0)
            {
                playerObject.GetComponent<boatControl>().gunActivate(false);
                zero = true;
                gunsActive = false;
            }
            if (currentCannonBall == 6 && OwnedLangrageShots == 0)
            {
                playerObject.GetComponent<boatControl>().gunActivate(false);
                zero = true;
                gunsActive = false;
            }
        }
        if (zero == false && gunsActive == false)
        {
            gameObject.GetComponent<boatControl>().gunActivate(true);
            gunsActive = true;
        }
    }
    public void buyRoundShots()
    {
        ShopMoney = ShopMoney - RoundShotPrice;
        OwnedRoundShots = OwnedRoundShots + 1;
        Camera.main.GetComponent<Shop>().MunitionsMenuToggle();
        zero = false;
    }
    public void buyCannisterShots()
    {
        ShopMoney = ShopMoney - CannisterShot;
        OwnedCannisterShots = OwnedCannisterShots + 1;
        Camera.main.GetComponent<Shop>().MunitionsMenuToggle();
        zero = false;
    }
    public void buyChainShots()
    {
        ShopMoney = ShopMoney - ChainShot;
        OwnedChainShots = OwnedChainShots + 1;
        Camera.main.GetComponent<Shop>().MunitionsMenuToggle();
        zero = false;
    }
    public void buyDoubleShots()
    {
        ShopMoney = ShopMoney - DoubleShotPrice;
        OwnedDoubleShots = OwnedDoubleShots + 1;
        Camera.main.GetComponent<Shop>().MunitionsMenuToggle();
        zero = false;
    }
    public void buyExplodingShell()
    {
        ShopMoney = ShopMoney - ExplodingShellPrice;
        OwnedExplodingShots = OwnedExplodingShots + 1;
        Camera.main.GetComponent<Shop>().MunitionsMenuToggle();
        zero = false;
    }
    public void buyLangrageShot()
    {
        ShopMoney = ShopMoney - LangrageShot;
        OwnedLangrageShots = OwnedLangrageShots + 1;
        Camera.main.GetComponent<Shop>().MunitionsMenuToggle();
        zero = false;
    }
    public void changeCannonball(int CannonType)
    {
        if (CannonType == 1)
        {
            playerObject.GetComponent<Ship>().changeCannonBallDamage(10, 0, 0);
        }
        if (CannonType == 2)
        {
            playerObject.GetComponent<Ship>().changeCannonBallDamage(14, 3, 1);
        }
        if (CannonType == 3)
        {
            playerObject.GetComponent<Ship>().changeCannonBallDamage(20, 0, 1);
        }
        if (CannonType == 4)
        {
            playerObject.GetComponent<Ship>().changeCannonBallDamage(6, 0, 4);
        }
        if (CannonType == 5)
        {
            playerObject.GetComponent<Ship>().changeCannonBallDamage(18, 1, 2);
        }
        if (CannonType == 6)
        {
            playerObject.GetComponent<Ship>().changeCannonBallDamage(4, 0, 0);
        }
    }
    //888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
    public void crewDie(int crew, int repairmen)
    {
        OwnedCrew = OwnedCrew - crew;
        crew = crew * -1;
        gameObject.GetComponent<boatControl>().crewChange(crew, shipValue);


        OwnedRepairmen = OwnedRepairmen - repairmen;
    }
    public void buyCrew()
    {
        OwnedCrew = OwnedCrew + 1;
        gameObject.GetComponent<boatControl>().crewChange(1, shipValue);

    }
    public void buyRepairmen()
    {
        OwnedRepairmen = OwnedRepairmen + 1;
        gameObject.GetComponent<boatControl>().gunActivate(true);

    }
    public void buyLooters()
    {
        OwnedLooters = OwnedLooters + 1;
    }
    public void crewDamage(int damage)
    {
        if (damage == 1)
        {
            int percentKill;
            percentKill = Random.Range(0, 2);
            if (percentKill == 1)
            {
                OwnedCrew = OwnedCrew - 1;
                crewDie(1, 0);
            }
            else
            {
                percentKill = Random.Range(0, 5);
                if (percentKill == 1)
                {
                    OwnedRepairmen = OwnedRepairmen - 1;
                    crewDie(0, 1);
                }
            }
        }
        if (damage == 2)
        {
            int percentKill;
            percentKill = Random.Range(0, 1);
            if (percentKill == 1)
            {
                OwnedCrew = OwnedCrew - 2;
                crewDie(2, 0);
            }
            else
            {
                percentKill = Random.Range(0, 4);
                if (percentKill == 1)
                {
                    OwnedRepairmen = OwnedRepairmen - 1;
                    crewDie(0, 1);
                }
            }
        }
        if (damage == 4)
        {
            int percentKill;
            percentKill = Random.Range(0, 1);
            if (percentKill == 1)
            {
                OwnedCrew = OwnedCrew - 4;
                crewDie(4, 0);
            }
            else
            {
                percentKill = Random.Range(0, 2);
                if (percentKill == 1)
                {
                    OwnedRepairmen = OwnedRepairmen - 2;
                    crewDie(0, 2);
                }
            }
        }
    }
    //88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
    void changeCannonBallAmount()
    {
        HudOwnedRoundShotText.text = OwnedRoundShots.ToString();
        HudOwnedCannisterShotText.text = OwnedCannisterShots.ToString();
        HudOwnedDoubleShotText.text = OwnedDoubleShots.ToString();
        HudOwnedLangrageShotText.text = OwnedLangrageShots.ToString();
        HudOwnedExplosiveShotText.text = OwnedExplodingShots.ToString();
        HudOwnedChainShotText.text = OwnedChainShots.ToString();
        HudSRound.text = OwnedRoundShots.ToString();
        HudSCannister.text = OwnedCannisterShots.ToString();
        HudSDouble.text = OwnedDoubleShots.ToString();
        HudSLangrage.text = OwnedLangrageShots.ToString();
        HudSExplosion.text = OwnedExplodingShots.ToString();
        HudSChain.text = OwnedChainShots.ToString();
    }
}

   
 
