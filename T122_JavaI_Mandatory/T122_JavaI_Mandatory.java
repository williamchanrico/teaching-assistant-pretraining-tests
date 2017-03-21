import java.lang.*;
import java.util.*;

class T122_JavaI_Mandatory{

    static Scanner sc = new Scanner(System.in);
    static Player player;

    public static void main(String args[]){
        int openingMenuInput;
        boolean firstTime = true;
        
        do{

            openingMenuInput = showOpeningMenu(firstTime);
        
            clear(0);

            switch(openingMenuInput){
                
                case 1:

                    if(!firstTime){
                        playGame();
                        break;
                    }

                    String inputName;
                    String inputGender;

                    do{
                        System.out.print("Input Your Name[5-20] : ");
                        inputName = sc.nextLine();
                    }while(inputName.length() < 5 || inputName.length() > 20 || !isAlpha(inputName));

                    do{
                        System.out.print("Input Your Gender[Male | Female] : ");
                        inputGender = sc.nextLine();
                    }while(!inputGender.equals("Male") && !inputGender.equals("Female"));

                    player = new Player(inputName, inputGender);

                    firstTime = false;
                    
                    playGame();

                    break;

                case 2:
                    showAboutGame();
                    break;

                case 3:
                    showExitSplash();
                    break;

            }

        }while(openingMenuInput != 3);

    }

    public static void playGame(){
        int inputMenu;

        do{
            clear(0);

            System.out.println("Welcome " + player.name);
            System.out.println("Your Stamina : " + player.stamina);
            System.out.println("=======================");
            System.out.println("1. Travel To Somewhere");
            System.out.println("2. View Player Profile");
            System.out.println("3. View Material List");
            System.out.println("4. Crafting");
            System.out.println("5. View Crafted Items");
            System.out.println("6. Sleep");
            System.out.println("7. Back To Menu");

            do{
                System.out.print("Choose : ");
                inputMenu = inputInt();
            }while(inputMenu < 1 || inputMenu > 7);

            switch(inputMenu){
                case 1:
                    travelToSomewhere();
                    sc.nextLine();
                    break;

                case 2:
                    showPlayerProfile();
                    break;

                case 3:
                    showMaterialList();
                    break;

                case 4:
                    craftItemMenu();
                    break;

                case 5:
                    showCraftedItems();
                    break;

                case 6:
                    recoverStamina();
                    break;

            }
        }while(inputMenu != 7);
    }

    public static void craftItemMenu(){
        int inputCraft;
        int inputType;

        clear(0);
        if(player.stamina < 40){
            System.out.println("You Are Too Tired To Do Crafting");
            sc.nextLine();
            return;
        }
        
        System.out.println("Which Item You Want To Craft");
        System.out.println("1. Weapon");
        System.out.println("2. Armor");
        do{
            System.out.print("Choose : ");
            inputCraft = inputInt();
        }while(inputCraft < 1 || inputCraft > 2);
        
        clear(0);

        switch(inputCraft){
            case 1:
                System.out.println("Craftable Weapon List");
                System.out.println("=====================");
                System.out.println("1. Iron Sword");
                System.out.println("2. Silver Sword");
                System.out.println("3. Gold Sword");
                do{
                    System.out.print("Choose : ");
                    inputType = inputInt();
                }while(inputType < 1 || inputType > 3);
                craftWeapon(inputType);
                break;
            case 2:
                System.out.println("Craftable Armor List");
                System.out.println("=====================");
                System.out.println("1. Iron Armor");
                System.out.println("2. Silver Armor");
                System.out.println("3. Gold Armor");
                do{
                    System.out.print("Choose : ");
                    inputType = inputInt();
                }while(inputType < 1 || inputType > 3);
                craftArmor(inputType);
                break;
        }
        sc.nextLine();
    }

    public static void craftWeapon(int weaponType){
        String inputCraft;
        int chance = -1;

        clear(0);
        switch(weaponType){
            case 1:
                System.out.println("Iron Sword");
                System.out.println("Required 3 Iron Ore And 1 Oak Wood");
                if(player.collectedMaterials.get("Iron Ore") >= 3 && player.collectedMaterials.get("Oak Wood") >= 1)
                    chance = 70;
                break;
            case 2:
                System.out.println("Silver Sword");
                System.out.println("Required 4 Silver Ore And 2 Aspen Wood");
                if(player.collectedMaterials.get("Silver Ore") >= 4 && player.collectedMaterials.get("Aspen Wood") >= 2)
                    chance = 50;
                break;
            case 3:
                System.out.println("Gold Sword");
                System.out.println("Required 5 Gold Ore And 3 Ancient Wood");
                if(player.collectedMaterials.get("Gold Ore") >= 5 && player.collectedMaterials.get("Ancient Wood") >= 3)
                    chance = 40;
                break;
        }

        do{
            System.out.print("Do You Want To Craft This Weapon?[Yes|No] : ");
            inputCraft = sc.nextLine();
        }while(!inputCraft.equals("Yes") && !inputCraft.equals("No"));
        

        if(inputCraft.equals("Yes")){
            clear(0);
            if(chance != -1){
                
                player.stamina -= 40;
                player.craftWeapon(weaponType, chance);

            }else{
                System.out.println("Your Material Isn't Enough");
            }

        }
    }

    public static void craftArmor(int armorType){
        String inputCraft;
        int chance = -1;

        clear(0);
        switch(armorType){
            case 1:
                System.out.println("Iron Armor");
                System.out.println("Required 3 Iron Ore And 1 Soft Leather");
                if(player.collectedMaterials.get("Iron Ore") >= 3 && player.collectedMaterials.get("Soft Leather") >= 1)
                    chance = 70;
                break;
            case 2:
                System.out.println("Silver Armor");
                System.out.println("Required 4 Silver Ore And 2 Thin Leather");
                if(player.collectedMaterials.get("Silver Ore") >= 4 && player.collectedMaterials.get("Thin Leather") >= 2)
                    chance = 50;
                break;
            case 3:
                System.out.println("Gold Armor");
                System.out.println("Required 5 Gold Ore And 3 Thick Leather");
                if(player.collectedMaterials.get("Gold Ore") >= 5 && player.collectedMaterials.get("Thick Leather") >= 3)
                    chance = 40;
                break;
        }

        do{
            System.out.print("Do You Want To Craft This Armor?[Yes|No] : ");
            inputCraft = sc.nextLine();
        }while(!inputCraft.equals("Yes") && !inputCraft.equals("No"));
        

        if(inputCraft.equals("Yes")){
            clear(0);
            if(chance != -1){
                
                player.stamina -= 40;
                player.craftArmor(armorType, chance);

            }else{
                System.out.println("Your Material Isn't Enough");
            }

        }
    }

    public static void recoverStamina(){
        clear(0);
        player.stamina = 100;
        System.out.println("Your Stamina Has Been Recovered");
        sc.nextLine();
    }

    public static void travelToSomewhere(){

        clear(0);

        if(player.stamina < 20){
            System.out.println("You Are Too Tired To Do Travelling");
            return;
        }else{
            player.stamina -= 20;
        }

        int choice;

        System.out.println("Which Area You Want To Explore :");
        System.out.println("1. Mountain Mouthspeak");
        System.out.println("2. Forrest Of Blue");
        System.out.println("3. Southern Hills");
        
        do{
            System.out.print("Choose : ");
            choice = inputInt();
        }while(choice < 1 || choice > 3);

        switch(choice){
            case 1:
                player.findStone(80);
                delay(500);

                player.findWood(40);
                delay(500);

                player.findLeather(60);
                delay(500);
                break;
            case 2:
                player.findStone(40);
                delay(500);

                player.findWood(80);
                delay(500);

                player.findLeather(60);
                delay(500);
                break;
            case 3:
                player.findStone(40);
                delay(500);

                player.findWood(60);
                delay(500);

                player.findLeather(80);
                delay(500);
                break;
        }

    }

    public static void showPlayerProfile(){
        clear(0);

        System.out.println("Player Profile");
        System.out.println("===================================");
        System.out.println("Name            : " + player.name);
        System.out.println("Gender          : " + player.gender);
        System.out.println("Level           : " + player.level);
        System.out.println("Experience      : " + player.experience + "/1000");
        System.out.printf("Crafting Status : - %d Crafting Done\n", player.craftingSucceed + player.craftingFailed);
        System.out.printf("                  - %d Success\n", player.craftingSucceed);
        System.out.printf("                  - %d Failed\n", player.craftingFailed);

        sc.nextLine();

    }

    public static void showMaterialList(){
        clear(0);
        
        Map<String, Integer> temp = sortByValue(player.collectedMaterials);

        System.out.println("Material Name                 Quantity");
        System.out.println("=======================================");
        for(Map.Entry<String, Integer> a : temp.entrySet()){
            System.out.printf("%-27s   %d\n", a.getKey(), a.getValue());
        }
        sc.nextLine();
    }

    private static HashMap sortByValue(HashMap x){
        List list = new LinkedList(x.entrySet());

        Comparator comparator = new Comparator(){
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        };

        Collections.sort(list, comparator);

        HashMap sortedHashMap = new LinkedHashMap();

        for(Iterator it=list.iterator();it.hasNext();){
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }

        return sortedHashMap;
    }

    public static void showCraftedItems(){
        clear(0);
        
        if(player.experience == 0 && player.level == 1){
            System.out.println("You Haven't Crafted Any Weapons Or Armors");
            sc.nextLine();
            return;
        }

        System.out.println("Crafted Items                 Quantity");
        System.out.println("=======================================");
        for(Map.Entry<String, Integer> a : player.craftedItems.entrySet()){
            if(a.getValue() != 0){
                System.out.printf("%-27s   %d\n", a.getKey(), a.getValue());
            }
        }
        sc.nextLine();
    }

    public static boolean isAlpha(String str){
        for(char a : str.toCharArray())
            if(!Character.isLetter(a))
                return false;
        return true;
    }

    public static int inputInt(){
        int input = -1;
        do{
            try{
                input = sc.nextInt();
            }catch(Exception e){
                input = -1;
                e.printStackTrace();
            }
            sc.nextLine();
        }while(input == -1);

        return input;
    }

    public static void showAboutGame(){
        clear(0);
        printTitle(0);
        for(int a=0;a<4;a++)
            System.out.print("\n");
        System.out.println("Crafter Journey is a game that all about crafting.");
        System.out.println("In this game you can craft many type of weapons and armors.");
        System.out.println("for example you can craft iron sword, iron armor and many more.");
        System.out.println("To craft the armor you need to have sufficient material and stamina.");
        System.out.println("To get material you can travel to a different place");
        System.out.println("to find the material like iron ore, ancient wood,");
        System.out.println("thick leather and many more.");
        System.out.println("So, Enjoy The Game :D");
        sc.nextLine();
    }

    public static void printTitle(int milliseconds){

        String[] openingSplash = new String[25];

        openingSplash[0] = "        _________                    _____   __";
        openingSplash[1] = "        \\_   ___ \\ _______ _____   _/ ____\\_/  |_   ____  _______";
        openingSplash[2] = "        /    \\  \\/ \\_  __ \\\\__  \\  \\   __\\ \\   __\\_/ __ \\ \\_  __ \\";
        openingSplash[3] = "        \\     \\____ |  | \\/ / __ \\_ |  |    |  |  \\  ___/  |  | \\/";
        openingSplash[4] = "         \\______  / |__|   (____  / |__|    |__|   \\___  > |__|";
        openingSplash[5] = "                \\/              \\/                     \\/";
        openingSplash[6] = "                ____.";
        openingSplash[7] = "               |    |  ____   __ __ _______   ____    ____   ___.__.";
        openingSplash[8] = "               |    | /  _ \\ |  |  \\\\_  __ \\ /    \\ _/ __ \\ <   |  |";
        openingSplash[9] = "           /\\__|    |(  <_> )|  |  / |  | \\/|   |  \\\\  ___/  \\___  |";
        openingSplash[10] = "           \\________| \\____/ |____/  |__|   |___|  / \\___  > / ____|";
        openingSplash[11] = "                                                 \\/      \\/  \\/";
    
        for(int a=0;a<12;a++){
            for(int b=0;b<openingSplash[a].length();b++){
                System.out.print(openingSplash[a].charAt(b));
                delay(milliseconds);
            }
            System.out.print("\n");
        }
    }

    public static void showExitSplash(){

        System.out.println("        $$$$$$$\\  $$\\                                             $$\\");
        System.out.println("        $$  __$$\\ $$ |                                            $$ |");
        System.out.println("        $$ |  $$ |$$ |$$\\   $$\\  $$$$$$\\  $$\\  $$$$$$\\   $$$$$$$\\ $$ |  $$\\");
        System.out.println("        $$$$$$$\\ |$$ |$$ |  $$ |$$  __$$\\ \\__| \\____$$\\ $$  _____|$$ | $$  |");
        delay(300);
        System.out.println("        $$  __$$\\ $$ |$$ |  $$ |$$$$$$$$ |$$\\  $$$$$$$ |$$ /      $$$$$$  /");
        System.out.println("        $$ |  $$ |$$ |$$ |  $$ |$$   ____|$$ |$$  __$$ |$$ |      $$  _$$<");
        System.out.println("        $$$$$$$  |$$ |\\$$$$$$  |\\$$$$$$$\\ $$ |\\$$$$$$$ |\\$$$$$$$\\ $$ | \\$$\\");
        System.out.println("        \\_______/ \\__| \\______/  \\_______|$$ | \\_______| \\_______|\\__|  \\__|");
        System.out.println("                                    $$\\   $$ |");
        delay(300);
        System.out.println("                                    \\$$$$$$  |");
        System.out.println("                                     \\______/");
        System.out.println("                            $$\\  $$$$$$$\\         $$\\");
        System.out.println("                          $$$$ | $$  ____|      $$$$ |");
        System.out.println("                          \\_$$ | $$ |           \\_$$ |");
        System.out.println("                            $$ | $$$$$$$\\ $$$$$$\\ $$ |");
        delay(300);
        System.out.println("                            $$ | \\_____$$\\\\______|$$ |");
        System.out.println("                            $$ | $$\\   $$ |       $$ |");
        System.out.println("                          $$$$$$\\$$$$$$  |     $$$$$$\\");
        System.out.println("                          \\______|\\______/      \\______|\n\n");


        System.out.println("                Keep Fighting and Share Our Greatest Skill");
        System.out.println("                                   By CJ15-1");
        sc.nextLine();
    }

    public static int showOpeningMenu(boolean firstTime){
        int openingMenuInput;

        clear(0);

        printTitle(5);

        for(int a=0;a<4;a++)
            System.out.print("\n");

        System.out.printf("1. %s\n", (firstTime ? "Play Game" : "Continue Game"));
        System.out.printf("2. About Game\n");
        System.out.printf("3. Exit\n");
        
        do{
            System.out.print("Choose: ");
                
            openingMenuInput = inputInt();
                
        }while(openingMenuInput < 1 || openingMenuInput > 3);

        return openingMenuInput;
    }

    public static void clear(int milliseconds){
        for(int a=0;a<25;a++){
            System.out.print("\n");
            delay(milliseconds);
        }
    }

    public static void delay(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}

class Player{
    String name;
    String gender;
    int level;
    int stamina;
    int experience;
    int craftingSucceed;
    int craftingFailed;

    HashMap<String, Integer> collectedMaterials;
    HashMap<String, Integer> craftedItems;

    Player(String name, String gender){
        craftedItems = new HashMap<String, Integer>();
        collectedMaterials = new HashMap<String, Integer>();
        this.name = name;
        this.gender = gender;
        level = 1;
        stamina = 100;
        experience = 0;
        craftingSucceed = 0;
        craftingFailed = 0;
        collectedMaterials.put("Iron Ore", 0);
        collectedMaterials.put("Silver Ore", 0);
        collectedMaterials.put("Gold Ore", 0);
        collectedMaterials.put("Oak Wood", 0);
        collectedMaterials.put("Aspen Wood", 0);
        collectedMaterials.put("Ancient Wood", 0);
        collectedMaterials.put("Soft Leather", 0);
        collectedMaterials.put("Thin Leather", 0);
        collectedMaterials.put("Thick Leather", 0);
    }

    void addExperience(int experience){
        if(this.experience + experience >= 1000){ 
            level++;
            System.out.println("You have leveled up to " + level);
        }
        this.experience = (this.experience + experience) % 1000;
    }

    void craftWeapon(int weaponType, int chance){
        Random random = new Random();
        int enhancedChance = chance + ((level - 1) * 5);
        
        switch(weaponType){
            case 1:
                if(random.nextInt(100) + 1 >= enhancedChance){
                    System.out.println("You Craft The Iron Sword!!!");
                    System.out.println("You Received 250 Experience!!");
                    addExperience(250);
                    if(!craftedItems.containsKey("Iron Sword"))
                        craftedItems.put("Iron Sword", 1);
                    else
                        craftedItems.put("Iron Sword", craftedItems.get("Iron Sword") + 1);
                    craftingSucceed++;
                }else{
                    System.out.println("You Failed To Craft The Sword");
                    craftingFailed++;
                }
                collectedMaterials.put("Iron Ore", collectedMaterials.get("Iron Ore") - 3);
                collectedMaterials.put("Oak Wood", collectedMaterials.get("Oak Wood") - 1);
                break;
            case 2:
                if(random.nextInt(100) + 1 >= enhancedChance){
                    System.out.println("You Craft The Silver Sword!!!");
                    System.out.println("You Received 250 Experience!!");
                    addExperience(250);
                    if(!craftedItems.containsKey("Silver Sword"))
                        craftedItems.put("Silver Sword", 1);
                    else
                        craftedItems.put("Silver Sword", craftedItems.get("Silver Sword") + 1);
                    craftingSucceed++;
                }else{
                    System.out.println("You Failed To Craft The Sword");
                    craftingFailed++;
                }
                collectedMaterials.put("Silver Ore", collectedMaterials.get("Silver Ore") - 4);
                collectedMaterials.put("Aspen Wood", collectedMaterials.get("Aspen Wood") - 2);
                break;
            case 3:
                if(random.nextInt(100) + 1 >= enhancedChance){
                    System.out.println("You Craft The Gold Sword!!!");
                    System.out.println("You Received 250 Experience!!");
                    addExperience(250);
                    if(!craftedItems.containsKey("Gold Sword"))
                        craftedItems.put("Gold Sword", 1);
                    else
                        craftedItems.put("Gold Sword", craftedItems.get("Gold Sword") + 1);
                    craftingSucceed++;
                }else{
                    System.out.println("You Failed To Craft The Sword");
                    craftingFailed++;
                }
                collectedMaterials.put("Gold Ore", collectedMaterials.get("Iron Ore") - 5);
                collectedMaterials.put("Ancient Wood", collectedMaterials.get("Ancient Wood") - 3);
                break;
        }
    }

    void craftArmor(int armorType, int chance){
        Random random = new Random();
        int enhancedChance = chance + ((level - 1) * 5);

        switch(armorType){
            case 1:
                if(random.nextInt(100) + 1 >= enhancedChance){
                    System.out.println("You Craft The Iron Armor!!!");
                    System.out.println("You Received 250 Experience!!");
                    addExperience(250);
                    if(!craftedItems.containsKey("Iron Armor"))
                        craftedItems.put("Iron Armor", 1);
                    else
                        craftedItems.put("Iron Armor", craftedItems.get("Iron Armor") + 1);
                    craftingSucceed++;
                }else{
                    System.out.println("You Failed To Craft The Armor");
                    craftingFailed++;
                }
                collectedMaterials.put("Iron Ore", collectedMaterials.get("Iron Ore") - 3);
                collectedMaterials.put("Soft Leather", collectedMaterials.get("Soft Leather") - 1);
                break;
            case 2:
                if(random.nextInt(100) + 1 >= enhancedChance){
                    System.out.println("You Craft The Silver Armor!!!");
                    System.out.println("You Received 250 Experience!!");
                    addExperience(250);
                    if(!craftedItems.containsKey("Silver Armor"))
                        craftedItems.put("Silver Armor", 1);
                    else
                        craftedItems.put("Silver Armor", craftedItems.get("Silver Armor") + 1);
                    craftingSucceed++;
                }else{
                    System.out.println("You Failed To Craft The Armor");
                    craftingFailed++;
                }
                collectedMaterials.put("Silver Ore", collectedMaterials.get("Silver Ore") - 4);
                collectedMaterials.put("Thin Leather", collectedMaterials.get("Thin Leather") - 2);
                break;
            case 3:
                if(random.nextInt(100) + 1 >= enhancedChance){
                    System.out.println("You Craft The Gold Armor!!!");
                    System.out.println("You Received 250 Experience!!");
                    addExperience(250);
                    if(!craftedItems.containsKey("Gold Armor"))
                        craftedItems.put("Gold Armor", 1);
                    else
                        craftedItems.put("Gold Armor", craftedItems.get("Gold Armor") + 1);
                    craftingSucceed++;
                }else{
                    System.out.println("You Failed To Craft The Armor");
                    craftingFailed++;
                }
                collectedMaterials.put("Gold Ore", collectedMaterials.get("Gold Ore") - 5);
                collectedMaterials.put("Thick Leather", collectedMaterials.get("Thick Leather") - 3);
                break;
        }
    }

    void findStone(int chance){
        Random random = new Random();
        if(random.nextInt(100) + 1 <= chance){
            int quantity = random.nextInt(10) + 1;

            switch(random.nextInt(3) + 1){
                case 1:
                    collectedMaterials.put("Iron Ore", collectedMaterials.get("Iron Ore") + quantity);
                    System.out.printf("You Have Found %d Iron Ore On The Journey\n\n", quantity);
                    break;
                case 2:
                    collectedMaterials.put("Silver Ore", collectedMaterials.get("Silver Ore") + quantity);
                    System.out.printf("You Have Found %d Silver Ore On The Journey\n\n", quantity);
                    break;
                case 3:
                    collectedMaterials.put("Gold Ore", collectedMaterials.get("Gold Ore") + quantity);
                    System.out.printf("You Have Found %d Gold Ore On The Journey\n\n", quantity);
                    break;

            }
        }else{
            System.out.print("Unfortunately You Didn\'t Find Any Stone\n\n");
        }
    }

    void findWood(int chance){
        Random random = new Random();
        if(random.nextInt(100) + 1 <= chance){
            int quantity = random.nextInt(10) + 1;

            switch(random.nextInt(3) + 1){
                case 1:
                    collectedMaterials.put("Oak Wood", collectedMaterials.get("Oak Wood") + quantity);
                    System.out.printf("You Have Found %d Oak Wood On The Journey\n\n", quantity);
                    break;
                case 2:
                    collectedMaterials.put("Aspen Wood", collectedMaterials.get("Aspen Wood") + quantity);
                    System.out.printf("You Have Found %d Aspen Wood On The Journey\n\n", quantity);
                    break;
                case 3:
                    collectedMaterials.put("Ancient Wood", collectedMaterials.get("Ancient Wood") + quantity);
                    System.out.printf("You Have Found %d Ancient Wood On The Journey\n\n", quantity);
                    break;

            }
        }else{
            System.out.print("Unfortunately You Didn\'t Find Any Wood\n\n");
        }
    }

    void findLeather(int chance){
        Random random = new Random();
        if(random.nextInt(100) + 1 <= chance){
            int quantity = random.nextInt(10) + 1;

            switch(random.nextInt(3) + 1){
                case 1:
                    collectedMaterials.put("Soft Leather", collectedMaterials.get("Soft Leather") + quantity);
                    System.out.printf("You Have Found %d Soft Leather On The Journey\n\n", quantity);
                    break;
                case 2:
                    collectedMaterials.put("Thin Leather", collectedMaterials.get("Thin Leather") + quantity);
                    System.out.printf("You Have Found %d Thin Leather On The Journey\n\n", quantity);
                    break;
                case 3:
                    collectedMaterials.put("Thick Leather", collectedMaterials.get("Thick Leather") + quantity);
                    System.out.printf("You Have Found %d Thick Leather On The Journey\n\n", quantity);
                    break;

            }
        }else{
            System.out.print("Unfortunately You Didn\'t Find Any Leather\n\n");
        }
    }
}