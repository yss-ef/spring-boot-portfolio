import { Component, OnInit, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chart, registerables } from 'chart.js';
import { forkJoin } from 'rxjs';
import { AccountService } from '../services/account-service';
import { CustomerService } from '../services/customer-service';

Chart.register(...registerables);

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css'
})
export class AdminDashboard implements OnInit {

  // Variables KPI
  totalCustomers: number = 0;
  totalAccounts: number = 0;
  totalBalance: number = 0;

  // Références Graphiques
  @ViewChild('chartDoughnut') doughnutRef!: ElementRef;
  @ViewChild('chartBar') barRef!: ElementRef;

  myDoughnutChart: any;
  myBarChart: any;

  constructor(
    private accountService: AccountService,
    private customerService: CustomerService,
    private cd: ChangeDetectorRef // Indispensable pour la mise à jour forcée
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    forkJoin({
      customers: this.customerService.searchCustomers(""),
      accounts: this.accountService.getAccounts()
    }).subscribe({
      next: (data: any) => {
        console.log("🔥 DONNÉES REÇUES:", data);

        // 1. EXTRACTION ROBUSTE (Gère 'Page' et 'List')
        const listCustomers = Array.isArray(data.customers) ? data.customers : (data.customers.content || []);
        const listAccounts = Array.isArray(data.accounts) ? data.accounts : (data.accounts.content || []);

        // 2. CALCUL DES KPI
        this.totalCustomers = listCustomers.length;
        this.totalAccounts = listAccounts.length;

        // Conversion explicite en Number pour éviter la concaténation de texte
        this.totalBalance = listAccounts.reduce((sum: number, acc: any) => {
          return sum + (Number(acc.balance) || 0);
        }, 0);

        // 3. GÉNÉRATION DES GRAPHIQUES
        this.createCharts(listAccounts);

        // 4. RAFRAÎCHISSEMENT FORCÉ DE L'ÉCRAN
        this.cd.detectChanges();
      },
      error: (err) => {
        console.error("Erreur chargement dashboard:", err);
      }
    });
  }

  createCharts(accounts: any[]) {
    if (!this.doughnutRef || !this.barRef) return;

    // FILTRAGE : On cherche 'SAV', 'SAVING', ou 'EPARGNE' pour les comptes épargne
    // Tout le reste est considéré comme compte courant
    const savingAccs = accounts.filter(acc =>
      ['SAV', 'SAVING', 'EPARGNE'].includes((acc.type || '').toUpperCase())
    );
    const currentAccs = accounts.filter(acc =>
      !['SAV', 'SAVING', 'EPARGNE'].includes((acc.type || '').toUpperCase())
    );

    // Calculs pour les graphiques
    const countSav = savingAccs.length;
    const countCur = currentAccs.length;
    const balanceSav = savingAccs.reduce((s, a) => s + (Number(a.balance) || 0), 0);
    const balanceCur = currentAccs.reduce((s, a) => s + (Number(a.balance) || 0), 0);

    // --- GRAPHIQUE 1 : DONUT ---
    if (this.myDoughnutChart) this.myDoughnutChart.destroy();
    this.myDoughnutChart = new Chart(this.doughnutRef.nativeElement, {
      type: 'doughnut',
      data: {
        labels: ['Épargne', 'Courant'],
        datasets: [{
          data: [countSav, countCur],
          backgroundColor: ['#198754', '#0d6efd'], // Vert / Bleu
          hoverOffset: 4
        }]
      },
      options: { responsive: true, maintainAspectRatio: false }
    });

    // --- GRAPHIQUE 2 : BARRES ---
    if (this.myBarChart) this.myBarChart.destroy();
    this.myBarChart = new Chart(this.barRef.nativeElement, {
      type: 'bar',
      data: {
        labels: ['Épargne', 'Courant'],
        datasets: [{
          label: 'Total des avoirs (MAD)',
          data: [balanceSav, balanceCur],
          backgroundColor: ['rgba(25, 135, 84, 0.7)', 'rgba(13, 110, 253, 0.7)'],
          borderColor: ['#198754', '#0d6efd'],
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: { y: { beginAtZero: true } }
      }
    });
  }
}
