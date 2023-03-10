import java.util.Random;
public class Main {
    public static int bossHealth = 1700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 260, 250, 180, 150, 100, 235, 280};
    public static int[] heroesDamage = {10, 15, 20, 0, 30, 60, 32, 15};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic",
                                                                              "G0lem", "Lucky", "Berserk", "Thor"};
    public static int roundNumber = 0;
    public static String message = "";
    public static boolean isFalse = false;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        message = "";
        chooseBossDefence();
        bossHits();
        heroesHit();
        medicCanHelp();
        printStatistics();
    }


    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }


    public static void bossHits() {
        Random mayBe = new Random();
        boolean lucky = mayBe.nextBoolean();
        boolean thor = mayBe.nextBoolean();
        for (int i = 0; i < heroesHealth.length; i++) {
            if ((heroesHealth[i] > 0) && thor != isFalse) {
                if (heroesHealth[i] - bossDamage < 0
                        || (heroesHealth[i] - ((bossDamage / 5) * (heroesAttackType.length) + bossDamage)) < 0) {
                    heroesHealth[i] = 0;
                } else if (heroesAttackType[i] == "G0lem" && lucky == isFalse) {
                    heroesHealth[i] = heroesHealth[i]-(bossDamage = (bossDamage / 5) * (heroesAttackType.length));
                } else if (lucky != isFalse && heroesAttackType[i] == "Lucky") {
                    heroesHealth[i] = heroesHealth[i] - (((bossDamage/5) * (heroesAttackType.length - 1)) + bossDamage);
                } else if (heroesAttackType[i] == "Berserk") {
                    heroesHealth[i] = heroesHealth[i] - (bossDamage / 3) * 2;
                } else {
                    heroesHealth[i] = heroesHealth[i] - (bossDamage / 5) * 4;
                }
            }
        }
        System.out.println("                          ");
        System.out.println(" You don't help ? " + thor);
        System.out.println("                          ");
    }

    public static void heroesHit() {
       double berserkSecurity = 1.5;
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2;
                    damage = damage * coefficient;
                    message = "Critical damage: " + damage;
                }
                if (bossDefence == "Medic") {
                    continue;
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;

                } else if (heroesAttackType[i] == "Berserk") {
                    bossHealth = bossHealth - ((bossDamage / 16 )* 11 + damage);
                }else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void medicCanHelp() {
        int medicHelp = 15;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && heroesHealth[i]
                    != heroesHealth[3] && heroesHealth[3] > 0) {
                heroesHealth[i] += medicHelp;
                break;
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ----------");

        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
        System.out.println(message);
    }

}
