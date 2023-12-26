package com.panneau.coupure.service;

import com.panneau.coupure.dto.ConsommationBatterie;
import com.panneau.coupure.dto.ConsommationPanneau;
import com.panneau.coupure.dto.Luminosite;
import com.panneau.coupure.dto.SecteurSourceBuffer;
import com.panneau.coupure.exceptions.LimiteConsommationException;
import com.panneau.coupure.model.Batterie;
import com.panneau.coupure.model.SourceSolaire;
import com.panneau.coupure.repository.SecteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SecteurSourceBufferService {
    @Autowired
    private SecteurRepository secteurRepository;

    @Autowired
    private SourceSolaireService sourceSolaireService;

    @Autowired
    private BatterieService batterieService;

    @Autowired
    private LuminositeService luminositeService;

    public void consommerPanneau(SecteurSourceBuffer secteurSourceBuffer, int idSecteur, double besoinEtudiants, Luminosite luminosite, double heure) throws LimiteConsommationException {
        ArrayList<SourceSolaire> sourceSolaires = sourceSolaireService.getSourceSolaireForSecteur(idSecteur);

        for (SourceSolaire sourceSolaire : sourceSolaires) {
            // Obtenir panneau en buffer
            int panneauIndex = secteurSourceBuffer.getPanneau(sourceSolaire.getPanneau().getId());

            double puisanceAccordee = luminositeService.obtenirPuissancePanneau(secteurSourceBuffer.getPanneaux().get(panneauIndex), luminosite);
            double reelConsommation = puisanceAccordee;

            double empruntBatterie = 0;
            double recharge = 0;
            // Check si la puissance accordee est plus petite que le besoin des eleves
            if (puisanceAccordee < besoinEtudiants) {
                empruntBatterie = Math.abs(besoinEtudiants - puisanceAccordee);

                reelConsommation -= empruntBatterie;
            } else {
                reelConsommation = besoinEtudiants;
                recharge = puisanceAccordee - besoinEtudiants;
            }

            ConsommationPanneau consommationPanneau = new ConsommationPanneau();
            consommationPanneau.setSecteur(secteurRepository.findById(Long.valueOf(idSecteur)).get());
            consommationPanneau.setConsommation(reelConsommation);
            consommationPanneau.setHeure(heure);
            consommationPanneau.setPanneau(secteurSourceBuffer.getPanneaux().get(panneauIndex));
            consommationPanneau.setEmpruntBatterie(empruntBatterie);
            consommationPanneau.setPuissanceAccordee(puisanceAccordee);
            consommationPanneau.setLuminosite(luminosite.getNiveauLuminosite());
            System.out.println("Panneau: " + puisanceAccordee + " | Consommation: " + reelConsommation);
            System.out.println("En manque: " + empruntBatterie);

            secteurSourceBuffer.ajouterConsommationPanneaux(consommationPanneau);

            consommerBatterie(secteurSourceBuffer, idSecteur, empruntBatterie, heure, recharge);
        }
    }

    public void consommerBatterie(SecteurSourceBuffer secteurSourceBuffer, int idSecteur, double consommation, double heure, double recharge) throws LimiteConsommationException {
        ArrayList<SourceSolaire> sourceSolaires = sourceSolaireService.getSourceSolaireForSecteur(idSecteur);

        for (SourceSolaire sourceSolaire : sourceSolaires) {
            // Obtenir la batterie en buffer
            int batterieIndex = secteurSourceBuffer.getBatterie(sourceSolaire.getBatterie().getId());

            // TODO: Ne marche pas avec plusieurs sources
            if (!secteurSourceBuffer.canBatterieBeConsummed(secteurSourceBuffer.getBatteries().get(batterieIndex).getId(), 50.0)) {
                ConsommationBatterie consommationBatterie = new ConsommationBatterie();
                consommationBatterie.setSecteur(secteurRepository.findById(Long.valueOf(idSecteur)).get());
                consommationBatterie.setConsommation(0);
                consommationBatterie.setBatterie(secteurSourceBuffer.getBatteries().get(batterieIndex));
                consommationBatterie.setHeure(heure);
                consommationBatterie.setReste(0);
                consommationBatterie.setRecharge(recharge);

                secteurSourceBuffer.ajouterComsommationBatterie(consommationBatterie);

                continue;
            }

            double consommationCourante = secteurSourceBuffer.getConsommationTotalPourBatterie(secteurSourceBuffer.getBatteries().get(batterieIndex).getId());
            double consommationTotale = consommationCourante + consommation;
            System.out.println("Conso total: " + consommationCourante);
            double reste = batterieService.consumeBatterie(secteurSourceBuffer.getBatteries().get(batterieIndex), consommationTotale);

            double consommationReelle = reste >= 0 ? consommation - reste : consommation;

            // Creation de consommation
            ConsommationBatterie consommationBatterie = new ConsommationBatterie();
            consommationBatterie.setSecteur(secteurRepository.findById(Long.valueOf(idSecteur)).get());
            consommationBatterie.setConsommation(consommationReelle);
            consommationBatterie.setBatterie(secteurSourceBuffer.getBatteries().get(batterieIndex));
            consommationBatterie.setHeure(heure);
            consommationBatterie.setReste(reste);
            consommationBatterie.setRecharge(recharge);

            secteurSourceBuffer.ajouterComsommationBatterie(consommationBatterie);
            System.out.println("Emprunt: " + consommationReelle + " | Reste: " + reste);

            if (reste >= 0) {
                System.out.println("Lany eto------------------------------");
                throw new LimiteConsommationException(consommationBatterie, consommation);
            }

            System.out.println(" ");
        }
    }
}
